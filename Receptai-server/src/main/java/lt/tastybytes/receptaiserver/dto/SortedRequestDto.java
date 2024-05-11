package lt.tastybytes.receptaiserver.dto;

import lt.tastybytes.receptaiserver.utils.Sorter;
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

    public Sorter toSorterOrDefault() {
        return new Sorter(new Sort.Order(getSortDirection(), getSortBy()));
    }

    public Sorter toSorterOrDefault(String defaultKey) {
        if (sortBy == null) {
            return new Sorter(new Sort.Order(getSortDirection(), defaultKey));
        }
        return toSorterOrDefault();
    }
}
