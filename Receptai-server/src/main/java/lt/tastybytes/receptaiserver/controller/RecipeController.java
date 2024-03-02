package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.recipe.*;
import lt.tastybytes.receptaiserver.dto.PublicUserDto;
import lt.tastybytes.receptaiserver.exception.NotFoundException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping(path="/api/v1/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @PostMapping(path="/create")
    public ResponseEntity<?> createNewRecipe(@Valid @RequestBody CreateRecipeDto dto, @AuthenticationPrincipal User user) {
       //if ()

        var newRecipe = recipeService.createRecipe(dto, user);



        //var user = userService.findUserByEmail(email);
        //if (user != null)
        //    return ResponseEntity.badRequest().build();

        //userService.createUser(username, email, password);

        return ResponseEntity.ok(newRecipe);
    }


    @GetMapping("/list")
    public Iterable<RecipeDto> getAllRecipes() {
        var recipes = recipeService.getAllRecipes();
        return recipes.stream().map(Recipe::toDto).toList();
    }

    @GetMapping("/getfake")
    public ResponseEntity<RecipeDto> getFakeRecipe(@RequestParam long id) {
        return ResponseEntity.ok(new RecipeDto(
                id,
                "Recepto pavadinimas",
                "Recepto trumpas aprasymas ir panasiai",
                new PublicUserDto("Vardenis Pavardenis"),
                new Date(),
                new Date(),
                "https://img.mazuma.lt/media/images/maxima/maxima_5f079612ae294f7595e2956a33e7620a23d212b936eeca3c07039453ef33b072.jpg",
                "https://www.youtube.com/embed/VkQajdYciW0",
                Arrays.asList(
                        new IngredientListDto("Main course", Arrays.asList(
                                new IngredientDto("pavadinimas", 1, "unit"),
                                new IngredientDto("pavadinimas", 2, "unit")
                        )),
                        new IngredientListDto("Padazas", Arrays.asList(
                                new IngredientDto("pavadinimas", 1, "unit"),
                                new IngredientDto("pavadinimas", 2, "unit")
                        ))
                ),
                Arrays.asList(
                        new InstructionDto("Pirma atsisedam ant kedes"),
                        new InstructionDto("Paskui atsisedam ir galvojam")
                ),
                Arrays.asList(new TagDto(1, "Nevalgomas maistas")),
                Arrays.asList(new CategoryDto(1, "Pavyzdine kategorija", true)),
                40,
                4
        ));
    }

    @GetMapping("/getfakelist")
    public ResponseEntity<Iterable<RecipeDto>> getFakeRecipeList() {
        var list = new ArrayList<RecipeDto>();

        var dto = new RecipeDto(
                -1L,
                "Recepto pavadinimas",
                "Recepto trumpas aprasymas ir panasiai",
                new PublicUserDto("Vardenis Pavardenis"),
                new Date(),
                null,
                "https://img.mazuma.lt/media/images/maxima/maxima_5f079612ae294f7595e2956a33e7620a23d212b936eeca3c07039453ef33b072.jpg",
                null,
                Arrays.asList(
                        new IngredientListDto("Main course", Arrays.asList(
                                new IngredientDto("pavadinimas", 1, "unit"),
                                new IngredientDto("pavadinimas", 2, "unit")
                        )),
                        new IngredientListDto("Padazas", Arrays.asList(
                                new IngredientDto("pavadinimas", 1, "unit"),
                                new IngredientDto("pavadinimas", 2, "unit")
                        ))
                ),
                Arrays.asList(
                        new InstructionDto("Pirma atsisedam ant kedes"),
                        new InstructionDto("Paskui atsisedam ir galvojam")
                ),
                Arrays.asList(new TagDto(1, "Nevalgomas maistas")),
                Arrays.asList(new CategoryDto(1, "Pavyzdine kategorija", true)),
                40,
                4
        );

        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);
        list.add(dto);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable(value = "id") long id) throws NotFoundException {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            throw new NotFoundException("Recipe by specified ID not found");
        }
        return ResponseEntity.ok(recipe.get().toDto());
    }

}
