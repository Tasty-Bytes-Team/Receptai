package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.Feedback;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT AVG(rating) FROM Feedback WHERE recipe.id = ?1")
    Optional<Double> averageOfRatings(long recipeId);

    @Query("SELECT AVG(rating) FROM Feedback WHERE recipe.author = ?1")
    Optional<Double> averageOfRatings(User author);

    Page<Feedback> findAllByRecipe(Recipe recipe, Pageable pageable);

    Optional<Feedback> findByRecipeAndAuthor(Recipe recipe, User author);

    Page<Feedback> findAllByAuthor(User author, Pageable pageable);
}