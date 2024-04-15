package lt.tastybytes.receptaiserver.service;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.SortedRequestDto;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RecipeService {
    /**
     * Returns recipe entity by specified ID.
     * @param id Recipe ID
     * @return Optional recipe entity.
     */
    Optional<Recipe> getRecipeById(long id);

    /**
     * Returns recipes for the specified page.
     * @param pageNumber Recipe page, starts at 0.
     * @return A page of recipes.
     */
    Page<Recipe> getRecipes(int pageNumber, @Valid @Nullable SortedRequestDto sortDto);
    Page<Recipe> getRecipesByCategory(Category category, int pageNumber);
    Page<Recipe> getRecipesByUser(User user, int pageNumber, @Valid @Nullable SortedRequestDto sortDto);


    Page<Recipe> findRecipeByQuery(String query, int pageNumber, @Valid @Nullable SortedRequestDto sortDto);


    RecipeDto createRecipe(@Valid ModifyRecipeDto dto, User author);
    RecipeDto editRecipe(Recipe recipe, @Valid ModifyRecipeDto dto);


    /**
     * @param id ID of the recipe to delete.
     * @return True if recipe was deleted successfully. False otherwise.
     */
    boolean deleteRecipeById(long id);
}
