package lt.tastybytes.receptaiserver.dto.recipe;

import lt.tastybytes.receptaiserver.dto.PublicUserDto;
import org.springframework.lang.Nullable;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

public record RecipeDto(
        int id,

        @NonNull String name,
        @NonNull String shortDescription,

        @NonNull PublicUserDto author,

        @NonNull Date dateCreated,

        @Nullable Date dateModified,

        @NonNull String previewImage,

        @Nullable String tutorialVideo,

        // TODO: figure out the structure of ingredients
        List<InstructionDto> instructions,

        List<TagDto> tags,

        @NonNull CategoryDto category,

        int minutesToPrepare,
        int portions
) { }

