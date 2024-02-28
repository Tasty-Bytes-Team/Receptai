package lt.tastybytes.receptaiserver.dto;

import org.springframework.lang.Nullable;
import org.springframework.lang.NonNull;

import java.util.Date;

public record RecipeDto(
        @NonNull String name,
        @NonNull String shortDescription,

        @NonNull PublicUserDto author,

        @NonNull Date dateCreated,

        @Nullable Date dateModified,

        @NonNull String previewImage,

        @Nullable String tutorialVideo,

        int minutesToPrepare,
        int portions
) { }

