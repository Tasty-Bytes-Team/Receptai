package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.model.Recipe;
import lt.tastybytes.receptaiserver.model.User;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    void createRecipe(String name, String description, User author);

    List<Recipe> getAllRecipes();
    Optional<Recipe> getRecipeById(long id);
}
