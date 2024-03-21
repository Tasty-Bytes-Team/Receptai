package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
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
    public ResponseEntity<?> createNewRecipe(
            @Valid @RequestBody ModifyRecipeDto dto,
            @AuthenticationPrincipal User user
    ) throws Exception {
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

    @PutMapping("/edit/{id}")
    public ResponseEntity<RecipeDto> editRecipe(
            @Valid @RequestBody ModifyRecipeDto dto,
            @PathVariable(value = "id") long id,
            @AuthenticationPrincipal User user
    ) throws Exception {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }

        if (!recipe.get().getAuthor().getId().equals(user.getId())) {
            throw new MissingRightsException("You cannot edit a recipe that is not yours!");
        }

        var newDto = recipeService.editRecipe(recipe.get(), dto);

        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponseDto> deleteRecipe(
            @PathVariable(value = "id") long id,
            @AuthenticationPrincipal User user
    ) throws Exception {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }

        if (!recipe.get().getAuthor().getId().equals(user.getId())) {
            throw new MissingRightsException("You cannot remove a recipe that is not yours!");
        }

        var ok = recipeService.deleteRecipeById(id);

        if (ok) {
            return ResponseEntity.ok(new MessageResponseDto("Recipe deleted successfully"));
        }

        return ResponseEntity.badRequest().body(new MessageResponseDto("Recipe deletion failed"));
    }

}
