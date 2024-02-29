package lt.tastybytes.receptaiserver.dto.user;

import org.springframework.lang.NonNull;

public record LoginRequestDto(@NonNull String email, @NonNull String password) {
}
