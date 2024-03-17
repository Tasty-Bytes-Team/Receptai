package lt.tastybytes.receptaiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.tastybytes.receptaiserver.controller.CategoryController;
import lt.tastybytes.receptaiserver.dto.category.CreateCategoryDto;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.service.CategoryService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryController.class)
@Import(CategoryControllerConfig.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCategoryTest() throws Exception {
        CreateCategoryDto createCategoryDto = new CreateCategoryDto("Test Category");

        Category mockedCategory = new Category("Test Category");
        Field field = Category.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(mockedCategory, 1L);

        given(categoryService.createCategory(any(CreateCategoryDto.class))).willReturn(mockedCategory);

        mockMvc.perform(post("/api/v1/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCategoryDto)))
                .andExpect(status().isOk());
    }

}
