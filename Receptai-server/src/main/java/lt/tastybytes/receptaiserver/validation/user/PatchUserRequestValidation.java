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

            int paramsPassed = 0;
            if (dto.newName() != null) paramsPassed++;
            if (dto.newEmail() != null) paramsPassed++;
            if (dto.newPassword() != null) paramsPassed++;
            if (dto.oldPassword() != null) paramsPassed++;
            if (dto.newProfileAvatarUrl() != null) paramsPassed++;

            if (paramsPassed == 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Please specify at least one parameter that you are patching."
                        ).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
