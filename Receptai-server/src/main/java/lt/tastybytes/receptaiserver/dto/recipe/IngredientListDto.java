package lt.tastybytes.receptaiserver.dto.recipe;

import java.util.List;

public record IngredientListDto(String purpose, List<IngredientDto> ingredients) {
}
