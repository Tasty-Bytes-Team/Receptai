package lt.tastybytes.receptaiserver.dto.recipe;

import org.springframework.lang.NonNull;

public record CategoryDto (long id, @NonNull String name, boolean primary) {
}
