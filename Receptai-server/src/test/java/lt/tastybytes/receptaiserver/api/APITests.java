package lt.tastybytes.receptaiserver.api;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.user.*;
import lt.tastybytes.receptaiserver.service.UserService;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private JwtServiceImpl jwtService;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeEach
    void createTestUsers() {
        userService.createUser("Normal User", "user@email.com", "Very Secret Password 123!");
        userService.createUser("Admin User", "admin@email.com", "Very Secret Password 123!");
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


    // Navigacija į neegzistuojančius API kelius:
    @Test
    void GetRequest_WhenEndpointDoesntExist_ShouldReturn401() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/this-does-not-exist", String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    // Iškvietimas į autorizacijos reikalaujančius API kelius be autorizacijos:
    @Test
    void GetRequestToUserMe_WhenUserIsNotAuthorized_ShouldReturn401() {
        var response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/user/me", String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    void GetRequestToUserMe_WhenUserIsAuthorizedWithUserRole_ShouldReturnUserData() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<FullUserDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/me", HttpMethod.GET, new HttpEntity<>(headers),
                FullUserDto.class);

        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Normal User", entity.getBody().name());
    }

    @Test
    void GetRequestToUserMe_WhenUserIsAuthorizedWithUserAndAdminRole_ShouldReturnUserData() throws Exception {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getAdminUserToken());
        ResponseEntity<FullUserDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/me", HttpMethod.GET, new HttpEntity<>(headers),
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
        ResponseEntity<FullUserDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/register", HttpMethod.POST, new HttpEntity<>(request, headers),
                FullUserDto.class);

        assertEquals(200, entity.getStatusCode().value());
    }

    @Test
    void PostToUserRegister_WhenEmailIsDuplicate_ShouldFail() {
        var request = new RegisterRequestDto("Pakartotinis vartotojas", "user@email.com", "TIKRAITEISINGAS");

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/register", HttpMethod.POST, new HttpEntity<>(request, headers),
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
        ResponseEntity<LoginResponseDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/login", HttpMethod.POST, new HttpEntity<>(request, headers),
                LoginResponseDto.class);
        assertEquals(200, entity.getStatusCode().value());
    }

    @Test
    void PostToUserLogin_WhenPasswordBad_ShouldFail() {
        var request = new LoginRequestDto("user@email.com", "1Very Secret Password 123!");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/login", HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(403, entity.getStatusCode().value());
    }

    @Test
    void PostToUserLogin_WhenEmailBad_ShouldFail() {
        var request = new LoginRequestDto("1user@email.com", "Very Secret Password 123!");
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        ResponseEntity<MessageResponseDto> entity = new TestRestTemplate().exchange(
                "http://localhost:" + port + "/api/v1/user/login", HttpMethod.POST, new HttpEntity<>(request, headers),
                MessageResponseDto.class);
        assertEquals(403, entity.getStatusCode().value());
    }


    // Vartotojo duomenų redagavimas:
    @Test
    void PatchToUserEdit1_WhenDataOk_ShouldSucceed() throws Exception {
        RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory());


        var request = new PatchUserDto("Antanas", null, null, null, null);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + getNormalUserToken());
        ResponseEntity<FullUserDto> entity = template.exchange(
                "http://localhost:" + port + "/api/v1/user/edit/1", HttpMethod.PATCH, new HttpEntity<>(request, headers),
                FullUserDto.class);
        assertEquals(200, entity.getStatusCode().value());
        assertEquals("Antanas", entity.getBody().name());
    }


    // Atsiliepimo sukūrimas prisijungus:

    // Recepto kūrimas:

    // Recepto redagavimas:

    // Recepto šalinimas:

}
