package lt.tastybytes.receptaiserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.lang.NonNull;

public record LoginRequestDto(
        @Email
        @NotEmpty
        @NonNull String email,

        @NotEmpty
        @Size(min = 8, message = "password should have at least 8 characters")
        @NonNull String password
) {
}
