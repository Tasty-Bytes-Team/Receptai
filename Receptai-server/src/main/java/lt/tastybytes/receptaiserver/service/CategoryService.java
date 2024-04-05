package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CategoryService {
    Page<Category> getCategories(int pageNumber);
    Optional<Category> getCategoryByName(String name);
    Optional<Category> getCategoryById(long id);
    Category createCategory(CreateCategoryDto dto);
}
