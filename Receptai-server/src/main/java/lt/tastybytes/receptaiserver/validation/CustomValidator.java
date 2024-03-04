package lt.tastybytes.receptaiserver.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValidation, String> {

    private CustomValidationPredicate predicate;

    @Override
    public void initialize(CustomValidation constraintAnnotation) {
        try {
            predicate = constraintAnnotation.value().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to instantiate predicate", e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return predicate.validate(value);
    }
}