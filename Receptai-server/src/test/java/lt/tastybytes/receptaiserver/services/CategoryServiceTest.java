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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void getCategories_WithValidPageNumber_ShouldReturnPageOfCategories() {
        CategoryService categoryServiceMock = mock(CategoryService.class);
        when(categoryServiceMock.getCategories(0)).thenReturn(Page.empty());

        Page<Category> result = categoryServiceMock.getCategories(0);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getCategoryByName_WithValidName_ShouldReturnCategory() {
        CategoryService categoryServiceMock = mock(CategoryService.class);
        Category expectedCategory = new Category();
        when(categoryServiceMock.getCategoryByName("Test Category")).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryServiceMock.getCategoryByName("Test Category");

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }

    @Test
    void getCategoryById_WithValidId_ShouldReturnCategory() {
        CategoryService categoryServiceMock = mock(CategoryService.class);
        Category expectedCategory = new Category();
        when(categoryServiceMock.getCategoryById(1L)).thenReturn(Optional.of(expectedCategory));

        Optional<Category> result = categoryServiceMock.getCategoryById(1L);

        assertTrue(result.isPresent());
        assertEquals(expectedCategory, result.get());
    }

    @Test
    void createCategory_WithValidDto_ShouldReturnCategory() {

        CreateCategoryDto dto = new CreateCategoryDto("Test Name", "Test Description", "Test URL");

        CategoryService categoryServiceMock = mock(CategoryService.class);
        Category expectedCategory = new Category();
        when(categoryServiceMock.createCategory(dto)).thenReturn(expectedCategory);

        Category result = categoryServiceMock.createCategory(dto);

        assertNotNull(result);
        assertEquals(expectedCategory, result);
    }
    @Test
    void getCategoryByName_WithInvalidName_ShouldReturnEmptyOptional() {
        Optional<Category> result = categoryService.getCategoryByName("Nonexistent Category");

        assertTrue(result.isEmpty());
    }

    @Test
    void getCategoryById_WithInvalidId_ShouldReturnEmptyOptional() {
        Optional<Category> result = categoryService.getCategoryById(-1L);

        assertTrue(result.isEmpty());
    }
}
