package lt.tastybytes.receptaiserver.dto;

import lt.tastybytes.receptaiserver.sorter.BaseSorter;
import lt.tastybytes.receptaiserver.validation.SortedRequestValidation;
import org.springframework.data.domain.Sort;


// TODO: the problem here is that I want to make this generic
// and I cannot pass generics to annotations
// will probably need some convoluted logic
public record SortedRequestDto<T extends Enum<T> & BaseSorter>(
        //@SortedRequestValidation.ValueOfEnum(enumClass = T.class)
        String sortBy,
        boolean sortAsc
) {
    public Sort.Direction getSortDirection() {
        if (sortAsc) return Sort.Direction.ASC;
        return Sort.Direction.DESC;
    }

    public String getSortBy() {
        return "dateCreated"; // TODO: right now it always sorts by dateCreated for whatever object which can fail
        //return sortBy.getColumnName();
    }
}
