package lt.tastybytes.receptaiserver.controller;

import lt.tastybytes.receptaiserver.model.Recipe;
import lt.tastybytes.receptaiserver.model.User;
import lt.tastybytes.receptaiserver.service.RecipeService;
import lt.tastybytes.receptaiserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @PostMapping(path="/create")
    public ResponseEntity createNewRecipe(
            @RequestParam String name,
            @RequestParam String description
    ) {

       var auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       System.out.println(auth);
       //if ()

        recipeService.createRecipe(name, description, (User) auth);



        //var user = userService.findUserByEmail(email);
        //if (user != null)
        //    return ResponseEntity.badRequest().build();

        //userService.createUser(username, email, password);

        return ResponseEntity.ok("Recipe created");
    }


    @GetMapping("/list")
    public @ResponseBody Iterable<Recipe> getAllRecipes() {
        // This returns a JSON or XML with the users
        return recipeService.getAllRecipes();
    }

}
