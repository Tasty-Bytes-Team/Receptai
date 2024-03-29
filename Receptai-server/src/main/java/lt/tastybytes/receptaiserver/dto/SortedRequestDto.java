package lt.tastybytes.receptaiserver.dto;

import jakarta.validation.constraints.NotEmpty;

public record SortedRequestDto(
        String sortBy,
        String sortAsc
) { }
