package lt.tastybytes.receptaiserver;

import jakarta.validation.Valid;

public record PagedRequestDto(
        @Valid
        int page
) { }
