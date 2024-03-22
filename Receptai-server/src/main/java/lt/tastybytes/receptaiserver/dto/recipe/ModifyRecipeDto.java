package lt.tastybytes.receptaiserver.dto.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lt.tastybytes.receptaiserver.validation.recipe.RecipeV;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@RecipeV.ModifyRecipeDtoValidation
@Valid
public record ModifyRecipeDto(
        @NotEmpty
        @NonNull String name,

        @NotEmpty
        @NonNull String shortDescription,

        @NotEmpty
        @NonNull String previewImage,

        @Nullable String tutorialVideo,

        @NotEmpty @Valid
        List<IngredientListDto> ingredients,

        @NotEmpty
        @Valid
        List<@Valid @NotEmpty @NotNull String> instructions,

        @NotEmpty
        List<Integer> tagIds,

        @NotNull
        @Positive
        int categoryId,

        @Positive
        int minutesToPrepare,

        @Positive
        int portions
) {
}
