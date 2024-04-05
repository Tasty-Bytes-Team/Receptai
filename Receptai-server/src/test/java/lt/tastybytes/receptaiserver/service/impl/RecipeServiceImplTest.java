package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.dto.SortedRequestDto;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.dto.tag.TagDto;
import lt.tastybytes.receptaiserver.dto.user.PublicUserDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.model.user.Role;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.repository.RecipeRepository;
import lt.tastybytes.receptaiserver.service.CategoryService;
import lt.tastybytes.receptaiserver.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// TODO: these tests are generated and will require thorough understanding of mocking
// TODO: some of them are broken for whatever reason org.mockito.exceptions.misusing.PotentialStubbingProblem
// TODO: those tests are commented out
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository mockRecipeRepository;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private TagService mockTagService;

    private RecipeServiceImpl recipeServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        recipeServiceImplUnderTest = new RecipeServiceImpl(mockRecipeRepository, mockCategoryService, mockTagService);
    }

    /*
    @Test
    void testCreateRecipe() {
        // Setup
        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);
        final User author = new User();
        author.setProfileUrl("avatarUrl");
        author.setEmail("email");
        author.setPassword("password");
        author.setName("name");
        final Role role = new Role();
        author.setRoles(List.of(role));

        final RecipeDto expectedResult = new RecipeDto(0L, "name", "shortDescription",
                new PublicUserDto(0L, "name", "avatarUrl"), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "previewImage", "tutorialVideo",
                "tutorialVideoEmbed",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of(new InstructionDto("text")), List.of(new TagDto(0L, "name", "iconName")),
                List.of(new CategoryDto(0L, "name", "description", "previewImageUrl")), 0, 0);

        // Configure CategoryService.getCategoryById(...).
        final Optional<Category> category = Optional.of(new Category("name", "description", "previewImageUrl"));
        when(mockCategoryService.getCategoryById(0L)).thenReturn(category);

        when(mockTagService.getTagById(0L)).thenReturn(Optional.of(new Tag("name", "iconName")));

        // Run the test
        final RecipeDto result = recipeServiceImplUnderTest.createRecipe(dto, author);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRecipeRepository).save(any(Recipe.class));
    }
     */

    @Test
    void testCreateRecipe_CategoryServiceReturnsAbsent() {
        // Setup
        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);
        final User author = new User();
        author.setProfileUrl("avatarUrl");
        author.setEmail("email");
        author.setPassword("password");
        author.setName("name");
        final Role role = new Role();
        author.setRoles(List.of(role));

        when(mockCategoryService.getCategoryById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> recipeServiceImplUnderTest.createRecipe(dto, author))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testCreateRecipe_TagServiceReturnsAbsent() {
        // Setup
        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);
        final User author = new User();
        author.setProfileUrl("avatarUrl");
        author.setEmail("email");
        author.setPassword("password");
        author.setName("name");
        final Role role = new Role();
        author.setRoles(List.of(role));

        // Configure CategoryService.getCategoryById(...).
        final Optional<Category> category = Optional.of(new Category("name", "description", "previewImageUrl"));
        when(mockCategoryService.getCategoryById(0L)).thenReturn(category);

        when(mockTagService.getTagById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> recipeServiceImplUnderTest.createRecipe(dto, author))
                .isInstanceOf(NoSuchElementException.class);
    }

    /*
    @Test
    void testEditRecipe() {
        // Setup
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        author.setId(1L);
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);

        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);
        final RecipeDto expectedResult = new RecipeDto(0L, "name", "shortDescription",
                new PublicUserDto(0L, "name", "avatarUrl"), new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "previewImage", "tutorialVideo",
                "tutorialVideoEmbed",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of(new InstructionDto("text")), List.of(new TagDto(0L, "name", "iconName")),
                List.of(new CategoryDto(0L, "name", "description", "previewImageUrl")), 0, 0);

        // Configure CategoryService.getCategoryById(...).
        final Optional<Category> category = Optional.of(new Category("name", "description", "previewImageUrl"));
        when(mockCategoryService.getCategoryById(0L)).thenReturn(category);

        when(mockTagService.getTagById(0L)).thenReturn(Optional.of(new Tag("name", "iconName")));

        // Run the test
        final RecipeDto result = recipeServiceImplUnderTest.editRecipe(recipe, dto);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockRecipeRepository).save(any(Recipe.class));
    }
    */


    @Test
    void testEditRecipe_CategoryServiceReturnsAbsent() {
        // Setup
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);

        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);
        when(mockCategoryService.getCategoryById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> recipeServiceImplUnderTest.editRecipe(recipe, dto))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testEditRecipe_TagServiceReturnsAbsent() {
        // Setup
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);

        final ModifyRecipeDto dto = new ModifyRecipeDto("name", "shortDescription", "previewImage", "tutorialVideo",
                List.of(new IngredientListDto("purpose", List.of(new IngredientDto("name", 0.0, "unit")))),
                List.of("value"), List.of(0), 0, 0, 0);

        // Configure CategoryService.getCategoryById(...).
        final Optional<Category> category = Optional.of(new Category("name", "description", "previewImageUrl"));
        when(mockCategoryService.getCategoryById(0L)).thenReturn(category);

        when(mockTagService.getTagById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> recipeServiceImplUnderTest.editRecipe(recipe, dto))
                .isInstanceOf(NoSuchElementException.class);
    }

/*
    @Test
    void testGetRecipeById() {
        // Setup
        // Configure RecipeRepository.findById(...).
        final Recipe recipe1 = new Recipe();
        recipe1.setName("name");
        recipe1.setDescription("shortDescription");
        recipe1.setPreviewImage("previewImage");
        recipe1.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe1.setAuthor(author);
        recipe1.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe1.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe1.setMinutesToPrepare(0);
        recipe1.setPortionCount(0);
        final Optional<Recipe> recipe = Optional.of(recipe1);
        when(mockRecipeRepository.findById(0L)).thenReturn(recipe);

        // Run the test
        final Optional<Recipe> result = recipeServiceImplUnderTest.getRecipeById(0L);

        // Verify the results
    }

    @Test
    void testGetRecipeById_RecipeRepositoryReturnsAbsent() {
        // Setup
        when(mockRecipeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Recipe> result = recipeServiceImplUnderTest.getRecipeById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testGetRecipes() {
        // Setup
        final SortedRequestDto sortDto = new SortedRequestDto("sortBy", false);

        // Configure RecipeRepository.findAll(...).
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);
        final Page<Recipe> recipes = new PageImpl<>(List.of(recipe));
        when(mockRecipeRepository.findAll(PageRequest.of(0, 1))).thenReturn(recipes);

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipes(0, sortDto);

        // Verify the results
    }

    @Test
    void testGetRecipes_RecipeRepositoryReturnsNoItems() {
        // Setup
        final SortedRequestDto sortDto = new SortedRequestDto("sortBy", false);
        when(mockRecipeRepository.findAll(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipes(0, sortDto);

        // Verify the results
    }

    @Test
    void testGetRecipesByCategory() {
        // Setup
        final Category category = new Category("name", "description", "previewImageUrl");

        // Configure RecipeRepository.findAllByCategoriesContaining(...).
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);
        final Page<Recipe> recipes = new PageImpl<>(List.of(recipe));
        when(mockRecipeRepository.findAllByCategoriesContaining(any(Category.class),
                eq(PageRequest.of(0, 1)))).thenReturn(recipes);

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipesByCategory(category, 0);

        // Verify the results
    }

    @Test
    void testGetRecipesByCategory_RecipeRepositoryReturnsNoItems() {
        // Setup
        final Category category = new Category("name", "description", "previewImageUrl");
        when(mockRecipeRepository.findAllByCategoriesContaining(any(Category.class),
                eq(PageRequest.of(0, 1)))).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipesByCategory(category, 0);

        // Verify the results
    }

    @Test
    void testGetRecipesByUser() {
        // Setup
        final User user = new User();
        user.setProfileUrl("avatarUrl");
        user.setEmail("email");
        user.setPassword("password");
        user.setName("name");
        final Role role = new Role();
        user.setRoles(List.of(role));

        // Configure RecipeRepository.findAllByAuthor(...).
        final Recipe recipe = new Recipe();
        recipe.setName("name");
        recipe.setDescription("shortDescription");
        recipe.setPreviewImage("previewImage");
        recipe.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe.setAuthor(author);
        recipe.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe.setMinutesToPrepare(0);
        recipe.setPortionCount(0);
        final Page<Recipe> recipes = new PageImpl<>(List.of(recipe));
        when(mockRecipeRepository.findAllByAuthor(any(User.class), eq(PageRequest.of(0, 1)))).thenReturn(recipes);

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipesByUser(user, 0);

        // Verify the results
    }

    @Test
    void testGetRecipesByUser_RecipeRepositoryReturnsNoItems() {
        // Setup
        final User user = new User();
        user.setProfileUrl("avatarUrl");
        user.setEmail("email");
        user.setPassword("password");
        user.setName("name");
        final Role role = new Role();
        user.setRoles(List.of(role));

        when(mockRecipeRepository.findAllByAuthor(any(User.class), eq(PageRequest.of(0, 1))))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // Run the test
        final Page<Recipe> result = recipeServiceImplUnderTest.getRecipesByUser(user, 0);

        // Verify the results
    }*/

    @Test
    void testDeleteRecipeById() {
        // Setup
        // Configure RecipeRepository.findById(...).
        final Recipe recipe1 = new Recipe();
        recipe1.setName("name");
        recipe1.setDescription("shortDescription");
        recipe1.setPreviewImage("previewImage");
        recipe1.setTutorialVideo("tutorialVideo");
        final User author = new User();
        recipe1.setAuthor(author);
        recipe1.setDateCreated(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe1.setDateModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        recipe1.setMinutesToPrepare(0);
        recipe1.setPortionCount(0);
        final Optional<Recipe> recipe = Optional.of(recipe1);
        when(mockRecipeRepository.findById(0L)).thenReturn(recipe);

        // Run the test
        final boolean result = recipeServiceImplUnderTest.deleteRecipeById(0L);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockRecipeRepository).deleteById(0L);
    }

    @Test
    void testDeleteRecipeById_RecipeRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockRecipeRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final boolean result = recipeServiceImplUnderTest.deleteRecipeById(0L);

        // Verify the results
        assertThat(result).isFalse();
    }
}
