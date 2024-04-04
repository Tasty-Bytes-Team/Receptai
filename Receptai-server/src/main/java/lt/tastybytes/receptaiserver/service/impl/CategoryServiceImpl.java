package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.repository.CategoryRepository;
import lt.tastybytes.receptaiserver.service.CategoryService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final int CATEGORIES_PER_PAGE = 20;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> getCategories(int pageNumber) {
        return categoryRepository.findAll(
                PageRequest.of(pageNumber, CATEGORIES_PER_PAGE)
        );
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findCategoryByName(name));
    }

    @Override
    public Optional<Category> getCategoryById(long id) {
        return Optional.ofNullable(categoryRepository.findCategoryById(id));
    }

    @Override
    public Category createCategory(CreateCategoryDto dto) {
        var category = new Category(dto.name().strip());
        categoryRepository.save(category);
        return category;
    }
}
