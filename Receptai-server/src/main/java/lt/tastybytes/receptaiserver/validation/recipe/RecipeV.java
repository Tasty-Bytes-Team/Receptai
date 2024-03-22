package lt.tastybytes.receptaiserver.validation.recipe;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.service.CategoryService;
import lt.tastybytes.receptaiserver.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class RecipeV {

    @Constraint(validatedBy = ModifyRecipeDtoValidator.class)
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface ModifyRecipeDtoValidation {
        String message() default "Validation failed";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class ModifyRecipeDtoValidator implements ConstraintValidator<ModifyRecipeDtoValidation, ModifyRecipeDto> {

        @Autowired
        CategoryService categoryService;

        @Autowired
        TagService tagService;

        @Override
        public boolean isValid(ModifyRecipeDto dto, ConstraintValidatorContext context) {

            //System.out.println("Calling custom validation");

            if (dto == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Null DTO? Something went horribly wrong")
                        .addConstraintViolation();
                return false;
            }

            // Validate the category
            if (categoryService.getCategoryById(dto.categoryId()).isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid category ID, such category does not exist")
                        .addConstraintViolation();
                return false;
            }

            // Validate tags
            for (var tagId : dto.tagIds()) {
                if (tagId == null) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Invalid tags specified")
                            .addConstraintViolation();
                    return false;
                }

                if (tagService.getTagById(tagId).isEmpty()) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Tag with ID of '%s' does not exist".formatted(tagId))
                            .addConstraintViolation();
                    return false;
                }
            }

            return true;
        }
    }

}
