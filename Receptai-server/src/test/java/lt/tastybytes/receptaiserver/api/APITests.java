package lt.tastybytes.receptaiserver.api;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.user.FullUserDto;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    void GetRequestToUserMe_WhenUserIsAuthorizedWithUserAdmin_ShouldReturnUserData() throws Exception {
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

    // El. pašto patvirtinimas:

    // Prisijungimas:

    // Vartotojo duomenų redagavimas:

    // Atsiliepimo sukūrimas prisijungus:

    // Recepto kūrimas:

    // Recepto redagavimas:

    // Recepto šalinimas:

}
