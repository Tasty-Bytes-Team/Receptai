package lt.tastybytes.receptaiserver.dto.user;

import jakarta.annotation.Nullable;

public record PublicUserDto(long id, String name, @Nullable String avatarUrl) { }
