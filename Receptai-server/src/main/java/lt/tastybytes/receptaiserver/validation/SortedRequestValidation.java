package lt.tastybytes.receptaiserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lt.tastybytes.receptaiserver.dto.SortedRequestDto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class SortedRequestValidation {

    @Constraint(validatedBy = Validator.class)
    @Retention(RUNTIME)
    @Target(PARAMETER)
    @Documented
    public @interface AllowedSortBy {
        String[] values();
        String message() default "Validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    private static class Validator implements ConstraintValidator<AllowedSortBy, SortedRequestDto> {

        private String[] allowedValues;

        @Override
        public void initialize(AllowedSortBy constraintAnnotation) {
            allowedValues = constraintAnnotation.values();
        }

        @Override
        public boolean isValid(SortedRequestDto dto, ConstraintValidatorContext context) {

            for (var val : allowedValues) {
                if (dto.sortBy().equals(val)) {
                    return true;
                }
            }

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Specified sortBy key is not valid. Valid keys are " +
                            String.join(", ", allowedValues))
                    .addConstraintViolation();
            return false;
        }
    }
}
