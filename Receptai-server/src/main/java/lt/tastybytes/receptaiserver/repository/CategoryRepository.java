package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);
}
