package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path="/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryDto dto) {
        var newTag = categoryService.createCategory(dto);
        return ResponseEntity.ok(newTag.toDto());
    }


    @GetMapping("/list")
    public Iterable<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories().stream().map(Category::toDto).toList();
    }
}
