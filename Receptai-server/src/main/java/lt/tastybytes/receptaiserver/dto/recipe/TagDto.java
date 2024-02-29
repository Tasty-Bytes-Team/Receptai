package lt.tastybytes.receptaiserver.dto.recipe;

import org.springframework.lang.NonNull;

public record TagDto(int id, @NonNull String name) {
}
