package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.dto.recipe.CategoryDto;
import lt.tastybytes.receptaiserver.dto.recipe.InstructionDto;
import lt.tastybytes.receptaiserver.dto.PublicUserDto;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.dto.recipe.TagDto;
import lt.tastybytes.receptaiserver.model.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(path="/api/v1/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @PostMapping(path="/create")
    public ResponseEntity createNewRecipe(
            @RequestParam String name,
            @RequestParam String description,
            @AuthenticationPrincipal User user
    ) {


       //if ()

        recipeService.createRecipe(name, description, user);



        //var user = userService.findUserByEmail(email);
        //if (user != null)
        //    return ResponseEntity.badRequest().build();

        //userService.createUser(username, email, password);

        return ResponseEntity.ok("Recipe created");
    }


    @GetMapping("/list")
    public @ResponseBody Iterable<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/getfake")
    public ResponseEntity<RecipeDto> getFakeRecipe(@RequestParam long id) {
        return ResponseEntity.ok(new RecipeDto(
                -1,
                "Recepto pavadinimas",
                "Recepto trumpas aprasymas ir panasiai",
                new PublicUserDto("Vardenis Pavardenis"),
                new Date(),
                new Date(),
                "https://img.mazuma.lt/media/images/maxima/maxima_5f079612ae294f7595e2956a33e7620a23d212b936eeca3c07039453ef33b072.jpg",
                "https://www.youtube.com/embed/VkQajdYciW0",
                new ArrayList<InstructionDto>(),
                new ArrayList<TagDto>(),
                new CategoryDto(1, "Pavyzdine kategorija"),
                40,
                4
        ));
    }

    @GetMapping("/getfakelist")
    public ResponseEntity<Iterable<RecipeDto>> getFakeRecipeList() {
        var list = new ArrayList<RecipeDto>();

        var dto = new RecipeDto(
                -1,
                "Recepto pavadinimas",
                "Recepto trumpas aprasymas ir panasiai",
                new PublicUserDto("Vardenis Pavardenis"),
                new Date(),
                null,
                "https://img.mazuma.lt/media/images/maxima/maxima_5f079612ae294f7595e2956a33e7620a23d212b936eeca3c07039453ef33b072.jpg",
                null,
                new ArrayList<InstructionDto>(),
                new ArrayList<TagDto>(),
                new CategoryDto(1, "Pavyzdine kategorija"),
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

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<Recipe> getRecipe(@RequestParam long id) {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe.get());
    }

}
