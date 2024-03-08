package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping(path="/create")
    public ResponseEntity<?> createNewRecipe(@Valid @RequestBody CreateRecipeDto dto, @AuthenticationPrincipal User user) {
       // TODO: any extra validation on DTO, if needed
        var newRecipe = recipeService.createRecipe(dto, user);
        return ResponseEntity.ok(newRecipe);
    }

    @GetMapping("/list")
    public Iterable<RecipeDto> getAllRecipes() {
        var recipes = recipeService.getAllRecipes();
        return recipes.stream().map(Recipe::toDto).toList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable(value = "id") long id) throws NotFoundException {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }
        return ResponseEntity.ok(recipe.get().toDto());
    }

}
