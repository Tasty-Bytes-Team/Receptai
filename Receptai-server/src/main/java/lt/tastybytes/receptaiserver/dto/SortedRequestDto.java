package lt.tastybytes.receptaiserver.dto;

import lt.tastybytes.receptaiserver.utils.Sorter;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class SortedRequestDto {

    @Nullable
    private final String sortBy;
    @Nullable
    private final boolean sortAsc;

    public SortedRequestDto(String sortBy, Boolean sortAsc) {
        this.sortBy = sortBy;
        this.sortAsc = Objects.requireNonNullElse(sortAsc, true);
    }

    @Nullable
    public String getSortBy() {
        return sortBy;
    }

    public Sort.Direction getSortDirection() {
        if (sortAsc) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    public Sorter toSorterOrDefault() {
        assert sortBy != null;
        return new Sorter(new Sort.Order(getSortDirection(), sortBy));
    }

    public Sorter toSorterOrDefault(String defaultKey) {
        if (sortBy == null) {
            return new Sorter(new Sort.Order(getSortDirection(), defaultKey));
        }
        return toSorterOrDefault();
    }
}
