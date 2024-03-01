package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.dto.recipe.CreateRecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.repository.RecipeRepository;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeDto createRecipe(CreateRecipeDto dto, User author) {
        var recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.shortDescription());
        recipe.setAuthor(author);
        recipe.setDateCreated(new Date());
        recipe.setMinutesToPrepare(dto.minutesToPrepare());
        recipe.setPortionCount(dto.portions());
        recipe.setPreviewImage(dto.previewImage());
        recipeRepository.save(recipe);
        return recipe.toDto();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> getRecipeById(long id) {
        return recipeRepository.findById(id);
    }
}
