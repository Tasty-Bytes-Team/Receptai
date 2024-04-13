package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.dto.user.PublicUserDto;
import lt.tastybytes.receptaiserver.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/feedback")
public class FeedbackController {

    @GetMapping(path="/getfakefeedback")
    public ResponseEntity<?> getFakeFeedback() {
        return ResponseEntity.ok(new FeedbackDto(
                new PublicUserDto(1L, "Fake User", null),
                "This is fake feedback content.",
                5
        ));
    }

    @GetMapping(path="/getfakefeedback2")
    public ResponseEntity<?> getFakeFeedback2() {
        return ResponseEntity.ok(new FeedbackDto(
                new PublicUserDto(1L, "Fake User", null),
                null,
                5
        ));
    }

    @PostMapping(path="/leave/{recipeId}")
    public ResponseEntity<?> leaveFeedback(
            @PathVariable long recipeId,
            @AuthenticationPrincipal User currentUser
    ) {
        return ResponseEntity.ok(new MessageResponseDto(
                "You asked to leave feedback for recipe of ID " + recipeId + " using user of " + currentUser.toPublicUserDto()
        ));
    }

}
