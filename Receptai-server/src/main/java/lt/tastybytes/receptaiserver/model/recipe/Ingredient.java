package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.recipe.IngredientDto;

@Entity
@Table(name = "recipe_ingredients")
public class Ingredient {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false)
    private String unit;

    @ManyToOne
    @JoinColumn(name = "ingredient_type_id")
    private IngredientType ingredientType;

    public IngredientDto toDto() {
        return new IngredientDto(name, quantity, unit);
    }

    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
