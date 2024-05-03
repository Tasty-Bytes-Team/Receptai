package lt.tastybytes.receptaiserver.api;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.dto.tag.CreateTagDto;
import lt.tastybytes.receptaiserver.dto.user.*;
import lt.tastybytes.receptaiserver.service.*;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class APITests {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void createTestUsers() {
        // Create 3 users
        userService.createUser("Normal User", "user@email.com", "Very Secret Password 123!");
        userService.createUser("Admin User", "admin@email.com", "Very Secret Password 123!");
        userService.createUser("Normal User 2", "user2@email.com", "Very Secret Password 123!");

        // Create a category and a tag to create a recipe
        categoryService.createCategory(new CreateCategoryDto("", "", null));
        tagService.createTag(new CreateTagDto("", ""));

        // Create a recipe 1 that is owned by user 2
        recipeService.createRecipe(new ModifyRecipeDto(
            "", "", "", null,
                List.of(), List.of(), List.of(1),
                1, 1, 1
        ), userService.findUserById(2).orElseThrow());

        // Leave a review on recipe 1 by user 3
        feedbackService.leaveFeedback(1, userService.findUserById(3).orElseThrow(), new CreateFeedbackDto(
                "", 8
        ));



        // TODO: there is no way to make someone an admin through the service yet
    }

    String getNormalUserToken() throws Exception {
        return jwtService.generateToken(userService.authenticate(
                "user@email.com", "Very Secret Password 123!"
        ));
    }

    String getAdminUserToken() throws Exception {
        return jwtService.generateToken(userService.authenticate(
                "admin@email.com", "Very Secret Password 123!"
        ));
    }

    String getNormalUser2Token() throws Exception {
        return jwtService.generateToken(userService.authenticate(
                "user2@email.com", "Very Secret Password 123!"
        ));
    }

    TestRestTemplate getTemplate() {
        // Automatically resolves HttpComponentsClientHttpRequestFactory()
        return new TestRestTemplate();
    }

    String getUrl(String endpoint) {
        return "http://localhost:" + port + endpoint;
    }


    // Navigacija į neegzistuojančius API kelius:
    @Test
    void GetToAnywhere_WhenEndpointDoesntExist_ShouldReturn401() {
        var response = restTemplate.getForEntity(getUrl("/api/v1/this-does-not-exist"), String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    // Iškvietimas į autorizacijos reikalaujančius API kelius be autorizacijos:
    @Test
    void GetToUserMe_WhenUserIsNotAuthorized_ShouldReturn401() {
        var response = restTemplate.getForEntity(getUrl("/api/v1/user/me"), String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    void GetToUserMe_WhenUserIsAuthorizedWithUserRole_ShouldReturnUserData() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<FullUserDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/me"), HttpMethod.GET, new HttpEntity<>(headers),
                FullUserDto.class);

        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Normal User", entity.getBody().name());
    }

    @Test
    void GetToUserMe_WhenUserIsAuthorizedWithUserAndAdminRole_ShouldReturnUserData() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getAdminUserToken());
        ResponseEntity<FullUserDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/me"), HttpMethod.GET, new HttpEntity<>(headers),
                FullUserDto.class);

        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Admin User", entity.getBody().name());
    }

    // Registracija:
    @Test
    void PostToUserRegister_WhenDataIsValid_ShouldSucceed() {
        var request = new RegisterRequestDto("Antanas Antanaitis", "Antanas@antanas.lt", "TIKRAITEISINGAS");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<FullUserDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/register"), HttpMethod.POST, new HttpEntity<>(request, headers),
                FullUserDto.class);

        assertEquals(200, entity.getStatusCode().value());
    }

    @Test
    void PostToUserRegister_WhenEmailIsDuplicate_ShouldFail() {
        var request = new RegisterRequestDto("Pakartotinis vartotojas", "user@email.com", "TIKRAITEISINGAS");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/register"), HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);

        assertEquals(400, entity.getStatusCode().value());
        assertThat(entity.getBody().message(), containsString("already exists"));
    }

    // TODO: not implemented
    // El. pašto patvirtinimas:

    // Prisijungimas:
    @Test
    void PostToUserLogin_WhenDetailsOk_ShouldSucceed() {
        var request = new LoginRequestDto("user@email.com", "Very Secret Password 123!");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<LoginResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/login"), HttpMethod.POST, new HttpEntity<>(request, headers),
                LoginResponseDto.class);
        assertEquals(200, entity.getStatusCode().value());
    }

    @Test
    void PostToUserLogin_WhenPasswordBad_ShouldFail() {
        var request = new LoginRequestDto("user@email.com", "1Very Secret Password 123!");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/login"), HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(403, entity.getStatusCode().value());
    }

    @Test
    void PostToUserLogin_WhenEmailBad_ShouldFail() {
        var request = new LoginRequestDto("1user@email.com", "Very Secret Password 123!");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/login"), HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(403, entity.getStatusCode().value());
    }


    // Vartotojo duomenų redagavimas:
    @Test
    void PatchToUserEdit1_WhenDataOk_ShouldSucceed() throws Exception {
        var request = new PatchUserDto("Antanas", null, null, null, null);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<FullUserDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/edit/1"), HttpMethod.PATCH, new HttpEntity<>(request, headers),
                FullUserDto.class);
        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Antanas", entity.getBody().name());
    }

    @Test
    void PatchToUserEdit1_WhenEmailIsDuplicate_ShouldFail() throws Exception {
        var request = new PatchUserDto(null, "admin@email.com", null, "Very Secret Password 123!", null);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/user/edit/1"), HttpMethod.PATCH, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(400, entity.getStatusCode().value());
        assertThat(entity.getBody().message(), containsString("already exists"));
    }


    // Atsiliepimo sukūrimas prisijungus:
    @Test
    void PostToFeedbackLeave1_WhenCreatingNewFeedbackOnOthersRecipe_ShouldSucceed() throws Exception {
        var request = new CreateFeedbackDto("Šitas receptas labai geras, visiems šeimoje patiko.", 10);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<FeedbackDto> entity = getTemplate().exchange(
                getUrl("/api/v1/feedback/leave/1"), HttpMethod.POST, new HttpEntity<>(request, headers),
                FeedbackDto.class);
        assertEquals(200, entity.getStatusCode().value());
    }

    // TODO: not yet implemented on the backend
    @Test
    void PostToFeedbackLeave1_WhenCreatingNewFeedbackOnOwnRecipe_ShouldFail() throws Exception {
        var request = new CreateFeedbackDto("Šitas receptas labai geras, visiems šeimoje patiko.", 10);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getAdminUserToken());
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/feedback/leave/1"), HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(400, entity.getStatusCode().value());
        assertThat(entity.getBody().message(), containsString("Cannot leave review on own recipe"));
    }

    @Test
    void PostToFeedbackLeave1_WhenCreatingDuplicateFeedback_ShouldFail() throws Exception {
        var request = new CreateFeedbackDto("Šitas receptas labai geras, visiems šeimoje patiko.", 10);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUser2Token());
        ResponseEntity<MessageResponseDto> entity = getTemplate().exchange(
                getUrl("/api/v1/feedback/leave/1"), HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(400, entity.getStatusCode().value());
        assertThat(entity.getBody().message(), containsString("User has already left"));
    }

    // Recepto kūrimas:

    // Recepto redagavimas:

    // Recepto šalinimas:

}
