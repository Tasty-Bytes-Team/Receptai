package lt.tastybytes.receptaiserver.dto.recipe;

import jakarta.validation.constraints.NotEmpty;
import lt.tastybytes.receptaiserver.dto.PublicUserDto;
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

        @NotEmpty
        List<IngredientListDto> ingredients,

        @NotEmpty
        List<String> instructions,

        @NotEmpty
        List<Integer> tagIds,

        @NotEmpty
        int categoryId,

        @NotEmpty
        int minutesToPrepare,

        @NotEmpty
        int portions
) {
}
