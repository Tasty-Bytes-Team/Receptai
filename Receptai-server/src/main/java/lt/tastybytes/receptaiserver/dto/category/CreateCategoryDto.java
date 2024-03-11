package lt.tastybytes.receptaiserver.dto.category;

import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryDto(
        @NotEmpty
        String name
) {}
