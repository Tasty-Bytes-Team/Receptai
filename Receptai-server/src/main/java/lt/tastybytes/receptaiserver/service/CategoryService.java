package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryByName(String name);
    Category createCategory(CreateCategoryDto dto);
}
