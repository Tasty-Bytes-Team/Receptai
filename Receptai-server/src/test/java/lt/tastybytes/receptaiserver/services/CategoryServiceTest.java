package lt.tastybytes.receptaiserver.services;

import lt.tastybytes.receptaiserver.TestDatabaseConfig;
import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void getCategories_WhenNoCategoriesExist_ShouldReturnEmptyPage() {
        Page<Category> result = categoryService.getCategories(0);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getCategoryByName_WhenValidNameProvided_ShouldReturnMatchingCategory() {
        categoryService.createCategory(new CreateCategoryDto("Test Category", "Description", "URL"));
        Category category = categoryService.getCategoryByName("Test Category").orElseThrow();
        assertEquals("Test Category", category.toDto().name());
    }

    @Test
    void getCategoryById_WhenValidIdProvided_ShouldReturnMatchingCategory() {
        Category createdCategory = categoryService.createCategory(new CreateCategoryDto("Test Category", "Description", "URL"));
        Category retrievedCategory = categoryService.getCategoryById(createdCategory.toDto().id()).orElseThrow();
        assertEquals(createdCategory.toDto().id(), retrievedCategory.toDto().id());
    }

    @Test
    void createCategory_WithValidDto_ShouldReturnCreatedCategory() {
        CreateCategoryDto dto = new CreateCategoryDto("Test Name", "Test Description", "Test URL");

        Category result = categoryService.createCategory(dto);

        assertNotNull(result);
        assertEquals(dto.name(), result.toDto().name());
        assertEquals(dto.description(), result.toDto().description());
        assertEquals(dto.previewImageUrl(), result.toDto().previewImageUrl());
    }

    @Test
    void getCategoryByName_WhenInvalidNameProvided_ShouldReturnEmptyOptional() {
        Optional<Category> result = categoryService.getCategoryByName("Nonexistent Category");
        assertTrue(result.isEmpty());
    }

    @Test
    void getCategoryById_WhenInvalidIdProvided_ShouldReturnEmptyOptional() {
        Optional<Category> result = categoryService.getCategoryById(-1L);
        assertTrue(result.isEmpty());
    }
}
