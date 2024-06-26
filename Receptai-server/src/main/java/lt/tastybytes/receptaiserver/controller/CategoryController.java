package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.service.CategoryService;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.utils.Pager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final RecipeService recipeService;

    public CategoryController(CategoryService categoryService, RecipeService recipeService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
    }

    @PostMapping(path="/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryDto dto) {
        var newTag = categoryService.createCategory(dto);
        return ResponseEntity.ok(newTag.toDto());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCategories(
            @Valid PagedRequestDto pageDto
    ) {
        var page = categoryService.getCategories(pageDto.page());
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Category::toDto)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategories(
            @PathVariable(value = "categoryId") long categoryId
    ) throws NotFoundException {
        var category = categoryService.getCategoryById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("Category by specified ID not found.");
        }
        return ResponseEntity.ok(
                category.get().toDto()
        );
    }

    @GetMapping("/{categoryId}/recipes")
    public ResponseEntity<?> getRecipesInCategory(
            @PathVariable(value = "categoryId") long categoryId,
            @Valid PagedRequestDto pageDto
    ) throws NotFoundException {
        var category = categoryService.getCategoryById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("Category by specified ID not found.");
        }

        var page = recipeService.getRecipesByCategory(category.get(), new Pager(pageDto));
        return ResponseEntity.ok(
            PagedResponseDto.of(page, Recipe::toDto)
        );
    }
}
