package lt.tastybytes.receptaiserver.dto;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.validation.PagedRequestValidation;
import org.springframework.lang.Nullable;

import java.util.Objects;
@PagedRequestValidation.PagedRequestDtoValidation
public class PagedRequestDto {
        @Valid
        @Nullable
        private int page;

        public PagedRequestDto(Integer page) {
            this.page = Objects.requireNonNullElse(page, 0);
        }

        public int page() {
                return page;
        }
}
