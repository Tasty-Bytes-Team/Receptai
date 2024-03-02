package lt.tastybytes.receptaiserver.dto.recipe;

import org.springframework.lang.NonNull;

public record TagDto(long id, @NonNull String name) {
}
