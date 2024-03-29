package lt.tastybytes.receptaiserver.dto.recipe;

import lt.tastybytes.receptaiserver.dto.user.PublicUserDto;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.dto.tag.TagDto;
import org.springframework.lang.Nullable;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

public record RecipeDto(
        Long id,
        @NonNull String name,
        @NonNull String shortDescription,

        @NonNull PublicUserDto author,

        @NonNull Date dateCreated,

        @Nullable Date dateModified,

        @NonNull String previewImage,

        @Nullable String tutorialVideo,
        @Nullable String tutorialVideoEmbed,

        @NonNull List<IngredientListDto> ingredients,

        @NonNull List<InstructionDto> instructions,

        @NonNull List<TagDto> tags,

        @NonNull List<CategoryDto> categories,

        int minutesToPrepare,
        int portions
) { }

