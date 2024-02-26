package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.model.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
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

    @GetMapping("/get")
    public @ResponseBody ResponseEntity<Recipe> getRecipe(@RequestParam long id) {
        var recipe =  recipeService.getRecipeById(id);
        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe.get());
    }

}
