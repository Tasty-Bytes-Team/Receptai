package lt.tastybytes.receptaiserver.dto;

import jakarta.validation.ConstraintValidatorContext;
import lt.tastybytes.receptaiserver.configs.ApplicationConfiguration;
import lt.tastybytes.receptaiserver.dto.recipe.IngredientListDto;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.service.CategoryService;
import lt.tastybytes.receptaiserver.service.TagService;
import lt.tastybytes.receptaiserver.validation.recipe.RecipeV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

// TODO: this bypasses default validation methods, I've sunk 3 hours into this single test already
// trying to figure out why services are not autowired
// Proper solution will be made in the future, for now it only tests the custom validator
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ApplicationConfiguration.class})
@MockitoSettings(strictness = Strictness.LENIENT)
public class ModifyRecipeDtoTest {
    @Mock
    private CategoryService categoryService;

    @Mock
    private TagService tagService;

    @Mock
    private RecipeV.ModifyRecipeDtoValidation validation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Autowired
    RecipeV.ModifyRecipeDtoValidator validator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validator = new RecipeV.ModifyRecipeDtoValidator();
        validator.initialize(validation);

        when(constraintValidatorContext.buildConstraintViolationWithTemplate(any())).thenReturn(
                new ConstraintValidatorContext.ConstraintViolationBuilder() {

                    @Override
                    public NodeBuilderDefinedContext addNode(String s) {
                        return null;
                    }

                    @Override
                    public NodeBuilderCustomizableContext addPropertyNode(String s) {
                        return null;
                    }

                    @Override
                    public LeafNodeBuilderCustomizableContext addBeanNode() {
                        return null;
                    }

                    @Override
                    public ContainerElementNodeBuilderCustomizableContext addContainerElementNode(String s, Class<?> aClass, Integer integer) {
                        return null;
                    }

                    @Override
                    public NodeBuilderDefinedContext addParameterNode(int i) {
                        return null;
                    }

                    @Override
                    public ConstraintValidatorContext addConstraintViolation() {
                        return null;
                    }
                }
        );
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                // Invalid YouTube URL
                Arguments.of(
                        "https://google.com/image.png",
                        "invalidURL",
                        // Ingredients
                        new ArrayList<>(),
                        // Instructions
                        List.of(),
                        // Tag IDs
                        List.of(),
                        1,  // Category ID
                        10, // Minutes to prepare
                        2,  // Portions
                        true
                ),
                // Valid URL
                Arguments.of(
                        "https://google.com/image.png",
                        "https://www.youtube.com/watch?v=Kes2fk-Nuwo",
                        // Ingredients
                        new ArrayList<>(),
                        // Instructions
                        List.of(),
                        // Tag IDs
                        List.of(),
                        1,  // Category ID
                        10, // Minutes to prepare
                        2,  // Portions
                        false
                ),
                // No YouTube URL provided at all
                Arguments.of(
                        "https://google.com/image.png",
                        null,
                        // Ingredients
                        new ArrayList<>(),
                        // Instructions
                        List.of(),
                        // Tag IDs
                        List.of(),
                        1,  // Category ID
                        10, // Minutes to prepare
                        2,  // Portions
                        false
                ),
                // Non existent category
                Arguments.of(
                        "https://google.com/image.png",
                        null,
                        // Ingredients
                        new ArrayList<>(),
                        // Instructions
                        List.of(),
                        // Tag IDs
                        List.of(),
                        200,  // Category ID
                        10, // Minutes to prepare
                        2,  // Portions
                        true
                ),
                // Non existent tag
                Arguments.of(
                        "https://google.com/image.png",
                        null,
                        // Ingredients
                        new ArrayList<>(),
                        // Instructions
                        List.of(),
                        // Tag IDs
                        List.of(999),
                        1,  // Category ID
                        10, // Minutes to prepare
                        2,  // Portions
                        true
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void createRecipe_WhenInvalidArguments_ShouldFail(
            String previewImage,
            String tutorialVideo,
            List<IngredientListDto> ingredients,
            List<String> instructions,
            List<Integer> tagIds,
            int categoryId,
            int minutesToPrepare,
            int portion,
            boolean shouldFail
    ) {
        when(categoryService.getCategoryById(anyLong())).thenReturn(
                Optional.empty()
        );
        when(categoryService.getCategoryById(1L)).thenReturn(
                Optional.of(new Category("name", "description", "previewImageUrl"))
        );
        when(tagService.getTagById(1L)).thenReturn(
                Optional.of(new Tag("tagName", "iconName"))
        );
        when(tagService.getTagById(anyLong())).thenReturn(
                Optional.empty()
        );


        var dto = new ModifyRecipeDto(
                "Valid name",
                "Valid description",
                previewImage,
                tutorialVideo,
                ingredients,
                instructions,
                tagIds,
                categoryId,
                minutesToPrepare,
                portion
        );

        assertNotNull(validator);

        ReflectionTestUtils.setField(validator, "categoryService", categoryService);
        ReflectionTestUtils.setField(validator, "tagService", tagService);

        boolean isValid = validator.isValid(dto, constraintValidatorContext);

        if (shouldFail) {
            assertEquals(false, isValid);
        } else {
            assertEquals(true, isValid);
        }
    }

}
