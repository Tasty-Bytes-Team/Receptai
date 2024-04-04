package lt.tastybytes.receptaiserver.service.impl;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.SortedRequestDto;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Ingredient;
import lt.tastybytes.receptaiserver.model.recipe.IngredientType;
import lt.tastybytes.receptaiserver.model.recipe.Instruction;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.repository.RecipeRepository;
import lt.tastybytes.receptaiserver.service.CategoryService;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    static final int RECIPES_PER_PAGE = 20;

    private final RecipeRepository recipeRepository;

    private final CategoryService categoryService;

    private final TagService tagService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, CategoryService categoryService, TagService tagService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @Override
    public RecipeDto createRecipe(ModifyRecipeDto dto, User author) {
        var recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.shortDescription());
        recipe.setAuthor(author);
        recipe.setDateCreated(new Date());
        recipe.setMinutesToPrepare(dto.minutesToPrepare());
        recipe.setPortionCount(dto.portions());
        recipe.setPreviewImage(dto.previewImage());

        recipe.setCategory(categoryService.getCategoryById(dto.categoryId()).orElseThrow());

        for (var tagId : dto.tagIds()) {
            if (tagId != null) {
                recipe.addTag(tagService.getTagById(tagId).orElseThrow());
            }
        }

        if (dto.tutorialVideo() == null || dto.tutorialVideo().isBlank()) {
            recipe.setTutorialVideo(null);
        } else {
            recipe.setTutorialVideo(dto.tutorialVideo().strip());
        }

        // Add instructions
        var instructions = dto.instructions();
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = new Instruction();
            instruction.setStepNo(i+1);
            instruction.setStepDescription(instructions.get(i));
            recipe.addInstruction(instruction);
        }
        // Add ingredients
        for (var ingredientList: dto.ingredients()) {
            var ingredientType = new IngredientType();
            ingredientType.setPurpose(ingredientList.purpose());
            for (var ingredient: ingredientList.ingredients()) {
                var ingredientObject = new Ingredient();
                ingredientObject.setName(ingredient.name());
                ingredientObject.setQuantity(ingredient.quantity());
                ingredientObject.setUnit(ingredient.unit());
                ingredientType.addIngredient(ingredientObject);
            }
            recipe.addIngredientType(ingredientType);
        }
        recipeRepository.save(recipe);
        return recipe.toDto();
    }

    @Override
    public RecipeDto editRecipe(Recipe recipe, ModifyRecipeDto dto) {
        recipe.setName(dto.name());
        recipe.setDescription(dto.shortDescription());
        recipe.setDateModified(new Date());
        recipe.setMinutesToPrepare(dto.minutesToPrepare());
        recipe.setPortionCount(dto.portions());
        recipe.setPreviewImage(dto.previewImage());

        recipe.setCategory(categoryService.getCategoryById(dto.categoryId()).orElseThrow());

        if (dto.tutorialVideo() == null || dto.tutorialVideo().isBlank()) {
            recipe.setTutorialVideo(null);
        } else {
            recipe.setTutorialVideo(dto.tutorialVideo().strip());
        }

        recipe.clearTags();
        for (var tagId : dto.tagIds()) {
            if (tagId != null) {
                recipe.addTag(tagService.getTagById(tagId).orElseThrow());
            }
        }

        // Add instructions
        recipe.clearInstructions();
        var instructions = dto.instructions();
        for (int i = 0; i < instructions.size(); i++) {
            var instruction = new Instruction();
            instruction.setStepNo(i+1);
            instruction.setStepDescription(instructions.get(i));
            recipe.addInstruction(instruction);
        }
        // Add ingredients
        recipe.clearIngredients();
        for (var ingredientList: dto.ingredients()) {
            var ingredientType = new IngredientType();
            ingredientType.setPurpose(ingredientList.purpose());
            for (var ingredient: ingredientList.ingredients()) {
                var ingredientObject = new Ingredient();
                ingredientObject.setName(ingredient.name());
                ingredientObject.setQuantity(ingredient.quantity());
                ingredientObject.setUnit(ingredient.unit());
                ingredientType.addIngredient(ingredientObject);
            }
            recipe.addIngredientType(ingredientType);
        }

        recipeRepository.save(recipe);
        return recipe.toDto();
    }

    @Override
    public Optional<Recipe> getRecipeById(long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Page<Recipe> getRecipes(int pageNumber, @Valid @Nullable SortedRequestDto sortDto) {
        var request = PageRequest.of(pageNumber, RECIPES_PER_PAGE);
        if (sortDto != null) {
            request = request.withSort(sortDto.getSortDirection(), sortDto.getSortBy());
        }
        return recipeRepository.findAll(request);
    }

    @Override
    public Page<Recipe> getRecipesByCategory(Category category, int pageNumber) {
        return recipeRepository.findAllByCategoriesContaining(
                category,
                PageRequest.of(pageNumber, RECIPES_PER_PAGE)
        );
    }

    @Override
    public Page<Recipe> getRecipesByUser(User user, int pageNumber) {
        return recipeRepository.findAllByAuthor(
                user,
                PageRequest.of(pageNumber, RECIPES_PER_PAGE)
        );
    }

    @Override
    public boolean deleteRecipeById(long id) {
        var recipe = getRecipeById(id);
        if (recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
