package lt.tastybytes.receptaiserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotEmpty
        @Size(min = 3, message = "name should have at least 3 characters")
        @Pattern(regexp = "^([a-zA-Z]+)(?:\\s[a-zA-Z]+)*$")
        String name,

        @NotEmpty
        @Email
        String email,

        @NotEmpty
        @Size(min = 6, message = "password should have at least 6 characters")
        String password
) {
}
