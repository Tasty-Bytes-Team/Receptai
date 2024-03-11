package lt.tastybytes.receptaiserver.model.category;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.category.CategoryDto;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, name, false);
    }
}
