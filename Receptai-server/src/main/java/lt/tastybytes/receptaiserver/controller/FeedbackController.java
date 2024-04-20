package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.Feedback;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.FeedbackService;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/feedback")
public class FeedbackController {

    private final RecipeService recipeService;
    private final FeedbackService feedbackService;

    public FeedbackController(RecipeService recipeService, FeedbackService feedbackService) {
        this.recipeService = recipeService;
        this.feedbackService = feedbackService;
    }

    @GetMapping(path="/list/{recipeId}")
    public ResponseEntity<PagedResponseDto<FeedbackDto>> listFeedbackForRecipe(
            @PathVariable long recipeId,
            @Valid PagedRequestDto pageDto
    ) throws NotFoundException {
        var optionalRecipe = recipeService.getRecipeById(recipeId);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException("Recipe with provided ID not found");
        }

        var page = feedbackService.getFeedbackByRecipe(recipeId, pageDto.page());
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Feedback::toDto)
        );
    }

    @PostMapping(path="/leave/{recipeId}")
    public ResponseEntity<FeedbackDto> leaveFeedback(
            @PathVariable long recipeId,
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CreateFeedbackDto dto
    ) throws NotFoundException {
        var optionalRecipe = recipeService.getRecipeById(recipeId);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException("Recipe with provided ID not found");
        }
        var createdFeedback = feedbackService.leaveFeedback(recipeId, currentUser, dto);
        return ResponseEntity.ok(createdFeedback.toDto());
    }
}
