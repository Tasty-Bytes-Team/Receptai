package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.model.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.repository.RecipeRepository;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void createRecipe(String name, String description, User author) {
        var recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setAuthor(author);
        recipeRepository.save(recipe);

    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
