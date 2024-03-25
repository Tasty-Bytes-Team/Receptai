package lt.tastybytes.receptaiserver.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.tastybytes.receptaiserver.validation.user.PatchUserRequestValidation;

@PatchUserRequestValidation.PatchUserDtoValidation
public record PatchUserDto(
        @Size(min = 3, message = "name should have at least 3 characters")
        @Pattern(regexp = "^([a-zA-Z]+)(?:\\s[a-zA-Z]+)*$")
        String newName,

        @Email
        String newEmail,

        @Size(min = 6, message = "password should have at least 6 characters")
        String newPassword,
        String oldPassword,
        String newProfileAvatarUrl
) {}
