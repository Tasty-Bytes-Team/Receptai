package lt.tastybytes.receptaiserver.dto.recipe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public record IngredientDto(
        @NotEmpty @NonNull String name,
        @Positive
        int quantity,
        @NotEmpty @NonNull String unit
) {
}
