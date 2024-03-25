package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.user.*;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.service.UserService;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtServiceImpl jwtService;

    @PostMapping(path="/register") // Map ONLY POST Requests
    public ResponseEntity<FullUserDto> registerNewUser(@Valid @RequestBody RegisterRequestDto dto) throws Exception {
        var user = userService.findUserByEmail(dto.email());
        if (user != null)
            throw new UserAlreadyExistsException();

        userService.createUser(dto.name(), dto.email(), dto.password());

        return ResponseEntity.ok(userService.findUserByEmail(dto.email()).toFullUserDto());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginRequestDto dto) throws Exception {
        User authenticatedUser = userService.authenticate(dto.email(), dto.password());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, jwtService.getExpirationTime(), authenticatedUser.toFullUserDto()));
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/recipes")
    public ResponseEntity<?> getUserRecipes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(recipeService.getAllUserRecipes(user).stream().map(Recipe::toDto).toList());
    }

    @GetMapping("/me")
    public ResponseEntity<FullUserDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.toFullUserDto());
    }

    @PatchMapping("/edit/{userId}")
    public ResponseEntity<FullUserDto> patchUser(
            @PathVariable(value = "userId") long userId,
            @Valid PatchUserDto dto,
            @AuthenticationPrincipal User currentUser
    ) throws NotFoundException, MissingRightsException {
        var maybeUser = userService.findUserById(userId);
        if (maybeUser.isEmpty()) {
            throw new NotFoundException("Specified user was not found");
        }

        var userToEdit = maybeUser.get();
        userToEdit.assertCanBeManagedBy(currentUser);

        var editedUser = userService.editUser(userToEdit, dto);

        return ResponseEntity.ok(editedUser.toFullUserDto());
    }
}
