package lt.tastybytes.receptaiserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;

public class CustomValidator implements ConstraintValidator<CustomValidation, String> {

    private CustomValidationPredicate predicate;

    @Override
    public void initialize(CustomValidation constraintAnnotation) {
        try {
            predicate = constraintAnnotation.value().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to instantiate predicate", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var isValid = predicate.validate(value);
        if (isValid) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(predicate.getErrorMessage())
                .addConstraintViolation();

        return false;
    }
}