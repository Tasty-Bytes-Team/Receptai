package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.recipe.IngredientListDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe_ingredient_types")
public class IngredientType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(mappedBy = "ingredientType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public IngredientListDto toDto() {
        return new IngredientListDto(purpose, getIngredients().stream().map(Ingredient::toDto).toList());
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
        ingredient.setIngredientType(this);
    }
}
