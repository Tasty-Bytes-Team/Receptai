package lt.tastybytes.receptaiserver.dto.user;

import lt.tastybytes.receptaiserver.validation.user.PatchUserRequestValidation;

@PatchUserRequestValidation.PatchUserDtoValidation
public record PatchUserDto(
        String newName,
        String newEmail,
        String newPassword,
        String oldPassword,
        String profileAvatarUrl
) {}
