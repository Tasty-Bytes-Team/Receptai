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
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String previewImageUrl;

    public Category() {

    }

    public Category(String name, String description, String previewImageUrl) {
        this.name = name;
        this.description = description;
        this.previewImageUrl = previewImageUrl;
    }

    public CategoryDto toDto() {
        return new CategoryDto(id, name, description, previewImageUrl);
    }
}
