package lt.tastybytes.receptaiserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class PagedRequestValidation {

    @Constraint(validatedBy = PagedRequestValidation.Validator.class)
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface PagedRequestDtoValidation {
        String message() default "Validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    private static class Validator implements ConstraintValidator<PagedRequestValidation.PagedRequestDtoValidation, PagedRequestDto> {

        @Override
        public boolean isValid(PagedRequestDto dto, ConstraintValidatorContext context) {
            if (dto == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Null DTO? Something went horribly wrong")
                        .addConstraintViolation();
                return false;
            }

            // Validate YouTube URL
            if (dto.page() < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Page number cannot be less than 0")
                        .addConstraintViolation();
                return false;
            }

            return true;
        }
    }

}
