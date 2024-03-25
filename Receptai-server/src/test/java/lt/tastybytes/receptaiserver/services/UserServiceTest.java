package lt.tastybytes.receptaiserver.services;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    void createTestUsers() {
        userService.createUser("Test User 1", "TestUser1@email.com", "Very Secret Password 123!");
        userService.createUser("Test User 2", "TestUser2@email.com", "A Very Secret Password 123!");
        userService.createUser("Test User 3", "TestUser3@email.com", "A Super Password 123!");
    }

    @Test
    void findAllUsers_WhenNoUsersExist_shouldReturn0Elements() throws Exception {
        var result = userService.findAllUsers();
        assertEquals(0, result.size());
    }

    @Test
    void findAllUsers_When1UserCreated_shouldReturn1Element() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var result = userService.findAllUsers();
        assertEquals(1, result.size());
    }

    @Test
    void newlyCreatedUserShouldHave1Role() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findAllUsers().get(0);
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void newlyCreatedUserShouldHaveDefaultRoleOfUser() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findAllUsers().get(0);
        assertEquals("ROLE_USER", user.getRoles().get(0).getName());
    }

    @Test
    void findUserByEmailForWrongEmailShouldReturnNull() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserByEmail("invalid@email.com");
        assertNull(user);
    }

    @Test
    void newlyCreatedUserShouldBeOfId1AndShouldReturnCorrectData() throws Exception {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserById(1).get();
        assertEquals(user.getEmail(), "TestUser@email.com");
        assertEquals(user.getName(), "Test User");
    }

    @Test
    void userEdit_ShouldBeAppliedWithValidOldPassword() throws NotFoundException {
        createTestUsers();
        var user = userService.findUserById(1).get();

        assertEquals("TestUser1@email.com", user.getEmail());

        var editedUser = userService.editUser(user, new PatchUserDto(
                "Naujas Vardas",
                "new@email.com",
                "newPassword1",
                "Very Secret Password 123!",
                "https://media.contentapi.ea.com/content/dam/gin/images/2017/01/command-and-conquer-red-alert-3-key-art.jpg.adapt.crop16x9.575p.jpg"
        ));


        assertEquals("Naujas Vardas", editedUser.getName());
        assertEquals("new@email.com", editedUser.getEmail());
    }

    @Test
    void userPasswordEdit_ShouldFailWithNoOldPassword() throws NotFoundException {
        createTestUsers();
        var user = userService.findUserById(1).get();
        assertThrows(BadCredentialsException.class, () -> {
            userService.editUser(user, new PatchUserDto(
                    null,
                    null,
                    "newPassword1",
                    null,
                    null
            ));
        });
    }

    @Test
    void userEmailEdit_ShouldFailWithNoOldPassword() throws NotFoundException {
        createTestUsers();
        var user = userService.findUserById(1).get();
        assertThrows(BadCredentialsException.class, () -> {
            userService.editUser(user, new PatchUserDto(
                    null,
                    "newemail@email.com",
                    null,
                    null,
                    null
            ));
        });
    }

    @Test
    void userPasswordEdit_ShouldFailWithBadOldPassword() throws NotFoundException {
        createTestUsers();
        var user = userService.findUserById(1).get();
        assertThrows(BadCredentialsException.class, () -> {
            userService.editUser(user, new PatchUserDto(
                    null,
                    null,
                    "newPassword1",
                    "badPassword",
                    null
            ));
        });
    }

    @Test
    void userPasswordEdit_ShouldWorkWithValidOldPassword() throws NotFoundException {
        createTestUsers();
        var user = userService.findUserById(1).get();
        userService.editUser(user, new PatchUserDto(
                    null,
                    null,
                    "newPassword1",
                    "Very Secret Password 123!",
                    null
        ));
    }

}
