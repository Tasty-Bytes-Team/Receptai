package lt.tastybytes.receptaiserver.validation.recipe;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;

public class RecipeV {

    @Constraint(validatedBy = ModifyRecipeDtoValidator.class)
    public @interface ModifyRecipeDtoValidation {
        String message() default "MyDTO validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    // Step 3: Create a validator for the custom constraint
    public static class ModifyRecipeDtoValidator implements ConstraintValidator<ModifyRecipeDtoValidation, ModifyRecipeDto> {
        @Override
        public boolean isValid(ModifyRecipeDto dto, ConstraintValidatorContext context) {
            if (dto == null) {

                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Kazkas blogai")
                        .addConstraintViolation();

                return false;
            }

            return true;
            // Custom validation logic for the whole DTO
            //return /* Your validation logic */;
        }
    }

}
