package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.DashboardDto;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.SortedRequestDto;
import lt.tastybytes.receptaiserver.dto.user.*;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.UserAlreadyExistsException;
import lt.tastybytes.receptaiserver.model.Feedback;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.FeedbackService;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.service.UserService;
import lt.tastybytes.receptaiserver.service.impl.JwtServiceImpl;
import lt.tastybytes.receptaiserver.validation.SortedRequestValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/user")
public class UserController {

    private final UserService userService;

    private final RecipeService recipeService;

    private final JwtServiceImpl jwtService;

    private final FeedbackService feedbackService;

    public UserController(
            UserService userService,
            RecipeService recipeService,
            JwtServiceImpl jwtService,
            FeedbackService feedbackService
    ) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.jwtService = jwtService;
        this.feedbackService = feedbackService;
    }

    @PostMapping(path="/register")
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
    public ResponseEntity<PagedResponseDto<User>> getAllUsers(
            @Valid PagedRequestDto dto
    ) {
        return ResponseEntity.ok(PagedResponseDto.of(
                userService.getUsers(dto.page())
        ));
    }

    @GetMapping("/recipes")
    public ResponseEntity<?> getUserRecipes(
            @AuthenticationPrincipal User user,
            @Valid PagedRequestDto pageDto,
            @Valid
            @SortedRequestValidation.AllowedSortBy(values={"name", "dateCreated"})
            SortedRequestDto sortDto
    ) {
        return ResponseEntity.ok(
                PagedResponseDto.of(
                        recipeService.getRecipesByUser(user, pageDto.page(), sortDto),
                        Recipe::toDto
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<FullUserDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.toFullUserDto());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> getCurrentUserDashboard(@AuthenticationPrincipal User user) {
        var recipePage = recipeService.getRecipesByUser(user, 0, new SortedRequestDto("dateCreated", false));
        var feedbackPage = feedbackService.getFeedbackByRecipeAuthor(user.getId(), 0);
        return ResponseEntity.ok(
                new DashboardDto(
                        recipePage.getTotalElements(),
                        recipePage.getContent().stream().map(Recipe::toDto).toList(), // TODO: limit to 5
                        feedbackPage.getContent().stream().map(Feedback::toDto).toList(), // TODO: sort
                        -1 // TODO: figure out how to obtain this
                )
        );
    }

    @PatchMapping("/edit/{userId}")
    public ResponseEntity<FullUserDto> patchUser(
            @PathVariable(value = "userId") long userId,
            @Valid @RequestBody PatchUserDto dto,
            @AuthenticationPrincipal User currentUser
    ) throws NotFoundException, MissingRightsException, UserAlreadyExistsException {
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
