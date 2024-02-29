package lt.tastybytes.receptaiserver.dto;

import org.springframework.lang.NonNull;

public record IngredientDto(@NonNull String name, int quantity, @NonNull String unit) {
}
