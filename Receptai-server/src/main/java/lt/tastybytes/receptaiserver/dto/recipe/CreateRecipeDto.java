package lt.tastybytes.receptaiserver.dto.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

public record CreateRecipeDto(
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
