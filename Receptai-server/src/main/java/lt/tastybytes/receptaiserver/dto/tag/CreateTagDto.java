package lt.tastybytes.receptaiserver.dto.tag;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

public record CreateTagDto(
        @NotEmpty
        @NonNull String name,

        @NotEmpty
        String iconName
) {}