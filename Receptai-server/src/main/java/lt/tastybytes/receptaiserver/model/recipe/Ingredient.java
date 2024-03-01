package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int quantity;
    private String unit;
}
