package lt.tastybytes.receptaiserver.dto.tag;

import org.springframework.lang.NonNull;

public record CreateTagDto(
        @NonNull String name,
        String iconName
) {}