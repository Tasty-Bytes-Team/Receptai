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
import lt.tastybytes.receptaiserver.utils.Pager;
import lt.tastybytes.receptaiserver.utils.Sorter;
import lt.tastybytes.receptaiserver.validation.SortedRequestValidation;
import org.springframework.data.domain.Sort;
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
        var sorter = sortDto.toSorterOrDefault("name");
        return ResponseEntity.ok(
                PagedResponseDto.of(
                        recipeService.getRecipesByUser(user, new Pager(pageDto), sorter),
                        Recipe::toDto
                )
        );
    }

    @GetMapping("/recipes/feedback")
    public ResponseEntity<?> getUserRecipeFeedback(
            @AuthenticationPrincipal User user,
            @Valid PagedRequestDto pageDto
    ) {
        var feedbackPage = feedbackService.getFeedbackByRecipeAuthor(
                user.getId(),
                new Pager(pageDto.page(), 20),
                new Sorter(new Sort.Order(Sort.Direction.DESC, "dateCreated"))
        );
        return ResponseEntity.ok(
                PagedResponseDto.of(
                        feedbackPage,
                        Feedback::toExtendedDto

                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<FullUserDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.toFullUserDto());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> getCurrentUserDashboard(@AuthenticationPrincipal User user) {
        var recipePage = recipeService.getRecipesByUser(
                user,
                new Pager(0, 5),
                new Sorter(new Sort.Order(Sort.Direction.DESC, "dateCreated"))
        );
        var feedbackPage = feedbackService.getFeedbackByRecipeAuthor(
                user.getId(),
                new Pager(0, 5),
                new Sorter(new Sort.Order(Sort.Direction.DESC, "dateCreated"))
        );
        return ResponseEntity.ok(
                new DashboardDto(
                        recipePage.getTotalElements(),
                        recipePage.getContent().stream().map(Recipe::toDto).toList(),
                        feedbackPage.getContent().stream().map(Feedback::toDto).toList(),
                        feedbackService.getAverageRecipeAuthorRating(user.getId())
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
