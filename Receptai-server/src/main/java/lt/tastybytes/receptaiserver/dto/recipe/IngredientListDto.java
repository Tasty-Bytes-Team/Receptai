package lt.tastybytes.receptaiserver.dto.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record IngredientListDto(
        @NotEmpty String purpose,
        @Valid
        @NotEmpty List<IngredientDto> ingredients) {
}
