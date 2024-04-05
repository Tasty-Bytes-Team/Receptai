package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/test")
public class TestController {

    @GetMapping(path="/kotlin")
    public ResponseEntity<?> testKotlinInterop() {
        return ResponseEntity.ok(new FeedbackDto("Kotlin", 21));
    }
}
