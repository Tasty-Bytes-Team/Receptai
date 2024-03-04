package lt.tastybytes.receptaiserver.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomValidation {
    String message() default "Custom validation error message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends CustomValidationPredicate<?>> value();
}