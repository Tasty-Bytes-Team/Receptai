package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_instructions")
public class RecipeInstruction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private int stepNo;
    private String stepDescription;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
