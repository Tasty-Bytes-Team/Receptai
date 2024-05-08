package lt.tastybytes.receptaiserver.services;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void createTestUsers() {
        userService.createUser("Test User 1", "TestUser1@email.com", "Very Secret Password 123!");
        userService.createUser("Test User 2", "TestUser2@email.com", "A Very Secret Password 123!");
        userService.createUser("Test User 3", "TestUser3@email.com", "A Super Password 123!");
    }

    User getTestUser1() {
        return userService.findUserById(1).get();
    }

    @Test
    void findAllUsers_When3UsersExist_shouldReturn3Elements() {
        var result = userService.getUsers(0);
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void findAllUsers_When1UserCreated_shouldReturn4Elements() {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var result = userService.getUsers(0);
        assertEquals(4, result.getTotalElements());
    }

    @Test
    void findFirstUser_WhenUserNewlyCreated_ShouldHave1Role() {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserById(4).orElseThrow();
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void findFirstUser_WhenUserNewlyCreated_ShouldHaveDefaultRoleOfUser() {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserById(1).orElseThrow();
        assertEquals("ROLE_USER", user.getRoles().get(0).getName());
    }

    @Test
    void findUserByEmail_WhenEmailIsWrong_ShouldReturnNull() {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserByEmail("invalid@email.com");
        assertNull(user);
    }

    @Test
    void findUserById_WhenUserNewlyCreated_ShouldBeOfId4AndReturnCorrectData() {
        userService.createUser("Test User", "TestUser@email.com", "Very Secret Password 123!");
        var user = userService.findUserById(4).get();
        assertEquals(user.getEmail(), "TestUser@email.com");
        assertEquals(user.getName(), "Test User");
    }

    @Test
    void userEdit_WithValidOldPassword_ShouldBeApplied() throws NotFoundException, UserAlreadyExistsException {
        var user = getTestUser1();
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
    void userPasswordEdit_WithNoOldPassword_ShouldFail() {
        var user = getTestUser1();
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
    void userNameEdit_WithNoOldPassword_ShouldSucceed() throws NotFoundException, UserAlreadyExistsException {
        var user = getTestUser1();
        userService.editUser(user, new PatchUserDto(
                "New Name",
                null,
                null,
                null,
                null
        ));
    }

    @Test
    void userEmailEdit_WithNoOldPassword_ShouldFail() {
        var user = getTestUser1();
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
    void userPasswordEdit_WithBadOldPassword_ShouldFail() {
        var user = getTestUser1();
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
    void userPasswordEdit_WithValidOldPassword_ShouldSucceed() throws NotFoundException, UserAlreadyExistsException {
        var user = getTestUser1();
        userService.editUser(user, new PatchUserDto(
                null,
                null,
                "newPassword1",
                "Very Secret Password 123!",
                null
        ));
    }

    @Test
    void userAuthentication_WithInvalidDetails_ShouldFail() {
        assertThrows(BadCredentialsException.class, () -> {
            userService.authenticate("invalidemail@email.co", "invalidpassword");
        });
    }

    @Test
    void userAuthentication_WithValidEmailAndInvalidPassword_ShouldFail() {
        assertThrows(BadCredentialsException.class, () -> {
            userService.authenticate("TestUser1@email.com", "invalidpassword");
        });
    }

    @Test
    void userAuthentication_WithValidEmailAndInvalidPassword_ShouldSucceed() throws Exception {
        var user = userService.authenticate("TestUser1@email.com", "Very Secret Password 123!");
        assertNotNull(user);
    }

}
