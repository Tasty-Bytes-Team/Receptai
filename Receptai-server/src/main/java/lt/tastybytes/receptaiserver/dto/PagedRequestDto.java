package lt.tastybytes.receptaiserver.dto;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.validation.PagedRequestValidation;

@PagedRequestValidation.PagedRequestDtoValidation
public record PagedRequestDto(
        @Valid
        int page
) { }
