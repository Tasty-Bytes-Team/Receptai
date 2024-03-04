package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
