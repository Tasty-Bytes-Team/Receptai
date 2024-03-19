package lt.tastybytes.receptaiserver.services;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void thereShouldBeNoUsersWhenNoUsersAreCreated() throws Exception {
        var result = userService.findAllUsers();
        assertEquals(0, result.size());
    }

    @Test
    void thereShouldBe1UserWhen1UserCreated() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var result = userService.findAllUsers();
        assertEquals(1, result.size());
    }
}
