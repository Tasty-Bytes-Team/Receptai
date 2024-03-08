package lt.tastybytes.receptaiserver.dto.tag;

import org.springframework.lang.NonNull;

public record TagDto(long id, @NonNull String name, String iconName) {
}
