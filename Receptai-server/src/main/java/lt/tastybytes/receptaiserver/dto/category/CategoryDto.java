package lt.tastybytes.receptaiserver.dto.category;

import org.springframework.lang.NonNull;

public record CategoryDto (long id, @NonNull String name, boolean primary) {
}
