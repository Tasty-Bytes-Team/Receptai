package lt.tastybytes.receptaiserver.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import org.springframework.data.domain.Page;

public record PagedResponseDto<T>(
        List<T> elements,
        long currentPage,
        long currentElementCount,
        long totalPageCount,
        long totalElementCount,
        long elementsPerPage
) {
    public static <T> PagedResponseDto<T> of(Page<T> page) {
        return new PagedResponseDto<>(
                page.getContent(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    public static <T, R> PagedResponseDto<R> of(Page<T> page, Function<? super T, ? extends R> mapper) {
        List<R> mappedContent = page.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PagedResponseDto<>(
                mappedContent,
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }
}
