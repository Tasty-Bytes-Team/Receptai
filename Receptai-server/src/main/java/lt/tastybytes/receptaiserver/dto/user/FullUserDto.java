package lt.tastybytes.receptaiserver.dto.user;

import jakarta.annotation.Nullable;

import java.util.List;

public record FullUserDto(
        long id,
        String name,
        String email,
        @Nullable String avatarUrl,
        List<String> roles
) {
}
