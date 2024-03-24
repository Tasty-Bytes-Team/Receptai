package lt.tastybytes.receptaiserver.validation.user;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lt.tastybytes.receptaiserver.dto.user.PatchUserDto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class PatchUserRequestValidation {

    @Constraint(validatedBy = Validator.class)
    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    public @interface PatchUserDtoValidation {
        String message() default "Validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    private static class Validator implements ConstraintValidator<PatchUserDtoValidation, PatchUserDto> {

        @Override
        public boolean isValid(PatchUserDto dto, ConstraintValidatorContext context) {

            dto.newName();
            dto.newEmail();
            dto.newPassword();
            dto.oldPassword();
            dto.profileAvatarUrl();

            /*
            if (dto.page() < 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Page number cannot be less than 0")
                        .addConstraintViolation();
                return false;
            }
             */

            return true;
        }
    }



}
