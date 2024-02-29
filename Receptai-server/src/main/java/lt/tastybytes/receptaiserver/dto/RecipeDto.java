package lt.tastybytes.receptaiserver.dto;

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

        List<InstructionDto> instructions,

        int minutesToPrepare,
        int portions
) { }

