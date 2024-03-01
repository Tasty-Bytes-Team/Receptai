package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class RecipeCategory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean primary;
}
