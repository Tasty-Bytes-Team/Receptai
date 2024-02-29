package lt.tastybytes.receptaiserver.dto.recipe;

import lt.tastybytes.receptaiserver.dto.PublicUserDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

public record CreateRecipeDto(
        @NonNull String name,
        @NonNull String shortDescription,

        @NonNull String previewImage,

        @Nullable String tutorialVideo,

        // TODO: figure out the structure of ingredients
        List<String> instructions,

        List<Integer> tagIds,

        int categoryId,
        int minutesToPrepare,
        int portions
) {
}
