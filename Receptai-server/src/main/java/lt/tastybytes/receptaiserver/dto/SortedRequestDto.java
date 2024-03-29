package lt.tastybytes.receptaiserver.dto;

import org.springframework.data.domain.Sort;

public record SortedRequestDto(
        String sortBy,
        boolean sortAsc
) {
    public Sort.Direction getSortDirection() {
        if (sortAsc) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    public String getSortBy() {
        return sortBy;
    }
}
