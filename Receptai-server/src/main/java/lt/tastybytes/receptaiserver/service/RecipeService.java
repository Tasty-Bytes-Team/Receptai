package lt.tastybytes.receptaiserver.service;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.recipe.ModifyRecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.utils.Pager;
import lt.tastybytes.receptaiserver.utils.Sorter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RecipeService {
    /**
     * Returns recipe entity by specified ID.
     * @param id Recipe ID
     * @return Optional recipe entity.
     */
    Optional<Recipe> getRecipeById(long id);

    Number getTotalRecipeCount();

    Page<Recipe> getRecipes(Pager pager, Sorter sorter);
    Page<Recipe> getRecipesByCategory(Category category, Pager pager);
    Page<Recipe> getRecipesByUser(User user, Pager pager, Sorter sorter);

    Page<Recipe> getRecommendedRecipesForRecipe(Recipe recipe);


    Page<Recipe> findRecipeByQuery(String query, Pager pager, Sorter sorter);


    RecipeDto createRecipe(@Valid ModifyRecipeDto dto, User author);
    RecipeDto editRecipe(Recipe recipe, @Valid ModifyRecipeDto dto);


    /**
     * @param id ID of the recipe to delete.
     * @return True if recipe was deleted successfully. False otherwise.
     */
    boolean deleteRecipeById(long id);
}
