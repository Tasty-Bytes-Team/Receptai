package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Page<Recipe> findAllByAuthor(User author, Pageable pageable);
    Page<Recipe> findAllByCategoriesContaining(Category categories, Pageable pageable);
    Page<Recipe> findAllByNameIsLikeIgnoreCaseOrDescriptionIsLikeIgnoreCase(String name, String description, Pageable pageable);
    void deleteById(long id);
}
