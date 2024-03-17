package lt.tastybytes.receptaiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.tastybytes.receptaiserver.controller.RecipeController;
import lt.tastybytes.receptaiserver.dto.*;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.dto.tag.TagDto;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(RecipeController.class)
@Import(RecipeControllerConfig.class)
@AutoConfigureMockMvc
@WithMockUser
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    void createRecipeTest() throws Exception {
        List<IngredientDto> ingredientDtos = List.of(
                new IngredientDto("Flour", 200, "grams"),
                new IngredientDto("Sugar", 100, "grams")
        );
        ModifyRecipeDto modifyRecipeDto = new ModifyRecipeDto(
                "Test Recipe",
                "A short description",
                "image.jpg",
                null,
                List.of(new IngredientListDto("Main", ingredientDtos)),
                List.of("Step 1", "Step 2"), List.of(1),
                1,
                30,
                4);

        Date now = new Date();
        RecipeDto expectedRecipeDto = new RecipeDto(
                1L,
                "Test Recipe",
                "A short description",
                new PublicUserDto(123L, "Author Name"),
                now,
                now,
                "image.jpg",
                null,
                List.of(new IngredientListDto("Main", ingredientDtos)),
                List.of(new InstructionDto("Step 1"), new InstructionDto("Step 2")),
                List.of(new TagDto(1L, "Tag Name", "Tag Icon")),
                List.of(new CategoryDto(1L, "Category Name", true)),
                30,
                4
        );


        given(recipeService.createRecipe(any(ModifyRecipeDto.class), any(User.class))).willReturn(expectedRecipeDto);

        mockMvc.perform(post("/api/v1/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(modifyRecipeDto))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void createRecipeWithMissingFieldsTest() throws Exception {
        List<IngredientDto> ingredientDtos = List.of(
                new IngredientDto("Flour", 200, "grams"),
                new IngredientDto("Sugar", 100, "grams")
        );
        ModifyRecipeDto incompleteRecipeDto = new ModifyRecipeDto(
                "",
                "",
                "image.jpg",
                "Url",
                 List.of(new IngredientListDto("Main", ingredientDtos)),
                 List.of("Step 1", "Step 2"),
                null,
                -1,
                0,
                4);

        mockMvc.perform(post("/api/v1/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(incompleteRecipeDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    @AutoConfigureMockMvc(addFilters = false)
    void createRecipeAsUnauthorizedUserTest() throws Exception {
        List<IngredientDto> ingredientDtos = List.of(
                new IngredientDto("Flour", 200, "grams"),
                new IngredientDto("Sugar", 100, "grams")
        );
        ModifyRecipeDto recipeDto = new ModifyRecipeDto(
                "Test Recipe",
                "A short description",
                "image.jpg",
                null,
                List.of(new IngredientListDto("Main", ingredientDtos)),
                List.of("Step 1", "Step 2"), List.of(1),
                1,
                30,
                4);


        mockMvc.perform(post("/api/v1/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipeDto)))
                        .andExpect(status().isForbidden());
    }
}
