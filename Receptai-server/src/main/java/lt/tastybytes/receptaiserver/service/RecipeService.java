package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.dto.recipe.CreateRecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.exception.ValidationException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    RecipeDto createRecipe(CreateRecipeDto dto, User author) throws ValidationException;
    List<Recipe> getAllRecipes();
    List<Recipe> getAllUserRecipes(User user);
    Optional<Recipe> getRecipeById(long id);
}
