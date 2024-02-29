package lt.tastybytes.receptaiserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotEmpty
        @Size(min = 3, message = "name should have at least 8 characters")
        String name,

        @NotEmpty
        @Email
        String email,

        @NotEmpty
        @Size(min = 8, message = "password should have at least 8 characters")
        String password
) {
}
