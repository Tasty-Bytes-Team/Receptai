package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.MessageResponseDto;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.SortedRequestDto;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.utils.Pager;
import lt.tastybytes.receptaiserver.validation.SortedRequestValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(path="/create")
    public ResponseEntity<?> createNewRecipe(
            @Valid @RequestBody ModifyRecipeDto dto,
            @AuthenticationPrincipal User user
    ) {
        var newRecipe = recipeService.createRecipe(dto, user);
        return ResponseEntity.ok(newRecipe);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getRecipes(
            @Valid PagedRequestDto pageDto,
            @Valid
            @SortedRequestValidation.AllowedSortBy(values={"name", "dateCreated"})
            SortedRequestDto sortDto
    ) {
        var sorter = sortDto.toSorterOrDefault("name");
        var recipes = recipeService.getRecipes(new Pager(pageDto), sorter);
        return ResponseEntity.ok(
                PagedResponseDto.of(recipes, Recipe::toDto)
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable(value = "id") long id) throws NotFoundException {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }
        return ResponseEntity.ok(recipe.get().toDto());
    }

    @GetMapping("/{id}/recommended")
    public ResponseEntity<PagedResponseDto<RecipeDto>> getRecommendedRecipes(@PathVariable(value = "id") long id) throws NotFoundException {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }
        var page =  recipeService.getRecommendedRecipesForRecipe(recipe.orElseThrow());
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Recipe::toDto)
        );
    }

    @GetMapping("/find/{query}")
    public ResponseEntity<PagedResponseDto<RecipeDto>> findRecipeByQuery(
            @PathVariable(value = "query") String query,
            @Valid PagedRequestDto pageDto,
            @Valid
            @SortedRequestValidation.AllowedSortBy(values={"name", "dateCreated"})
            SortedRequestDto sortDto
    ) {
        var sorter = sortDto.toSorterOrDefault("name");
        var recipes = recipeService.findRecipeByQuery(query, new Pager(pageDto), sorter);
        return ResponseEntity.ok(
                PagedResponseDto.of(recipes, Recipe::toDto)
        );
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

        recipe.get().assertCanBeManagedBy(user);

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

        recipe.get().assertCanBeManagedBy(user);

        var ok = recipeService.deleteRecipeById(id);
        if (ok) {
            return ResponseEntity.ok(new MessageResponseDto("Recipe deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponseDto("Recipe deletion failed"));
    }

}
