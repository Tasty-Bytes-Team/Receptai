package lt.tastybytes.receptaiserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.tastybytes.receptaiserver.controller.RecipeController;
import lt.tastybytes.receptaiserver.dto.*;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import lt.tastybytes.receptaiserver.dto.tag.TagDto;
import lt.tastybytes.receptaiserver.model.recipe.Instruction;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.net.http.WebSocketHandshakeException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldCreateRecipeSuccessfully() throws Exception {
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

        MvcResult creationResult = mockMvc.perform(post("/api/v1/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRecipeDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        String creationResponseContent = creationResult.getResponse().getContentAsString();
        RecipeDto createdRecipeDto = objectMapper.readValue(creationResponseContent, RecipeDto.class);
        Long createdRecipeId = createdRecipeDto.id();


        MvcResult fetchResult = mockMvc.perform(get("/api/v1/recipe/get/" + createdRecipeId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();


        String fetchResponseContent = fetchResult.getResponse().getContentAsString();
        RecipeDto fetchedRecipeDto = objectMapper.readValue(fetchResponseContent, RecipeDto.class);
        assertEquals(createdRecipeDto, fetchedRecipeDto);
    }



    @Test
    @WithMockUser(roles = {"USER"})
    void shouldRejectCreationOfRecipeWithMissingFields() throws Exception {
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
    void shouldReturnNotFoundWhenRecipeDoesNotExist() throws Exception {
        // Given
        long invalidRecipeId = 999L;
        when(recipeService.getRecipeById(invalidRecipeId)).thenReturn(Optional.empty());


        mockMvc.perform(get("/api/v1/recipe/get/{id}", invalidRecipeId))
                .andExpect(status().isNotFound());
    }

}
