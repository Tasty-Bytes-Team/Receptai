package lt.tastybytes.receptaiserver.dto.user;

import jakarta.annotation.Nullable;

public record FullUserDto(
        long id,
        String name,
        String email,
        @Nullable String avatarUrl
) {
}
