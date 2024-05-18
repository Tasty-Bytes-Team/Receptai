package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.feedback.CreateFeedbackDto;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.exception.RuntimeValidationException;
import lt.tastybytes.receptaiserver.model.Feedback;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.FeedbackService;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.utils.Pager;
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

        var page = feedbackService.getFeedbackByRecipe(recipeId, new Pager(pageDto));
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Feedback::toDto)
        );
    }

    @GetMapping(path="/list")
    public ResponseEntity<PagedResponseDto<FeedbackDto>> listFeedback(
            @Valid PagedRequestDto pageDto
    ) {
        var page = feedbackService.getFeedback(new Pager(pageDto));
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Feedback::toDto)
        );
    }

    @PostMapping(path="/leave/{recipeId}")
    public ResponseEntity<FeedbackDto> leaveFeedback(
            @PathVariable long recipeId,
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CreateFeedbackDto dto
    ) throws NotFoundException, RuntimeValidationException {
        var optionalRecipe = recipeService.getRecipeById(recipeId);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException("Recipe with provided ID not found");
        }

        if (optionalRecipe.get().getAuthor().getId().equals(currentUser.getId())) {
            throw new RuntimeValidationException("You cannot leave feedback on your own recipe!");
        }

        var createdFeedback = feedbackService.leaveFeedback(recipeId, currentUser, dto);
        return ResponseEntity.ok(createdFeedback.toDto());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponseDto> deleteFeedback(
            @PathVariable(value = "id") long id,
            @AuthenticationPrincipal User user
    ) throws Exception {
        var feedback = feedbackService.getFeedbackById(id);
        if (feedback.isEmpty()) {
            throw new NotFoundException("Feedback by specified ID not found");
        }

        feedback.get().assertCanBeManagedBy(user);

        var ok = feedbackService.deleteFeedbackById(id);
        if (ok) {
            return ResponseEntity.ok(new MessageResponseDto("Feedback deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponseDto("Feedback deletion failed"));
    }


}
