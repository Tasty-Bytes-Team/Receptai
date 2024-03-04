package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.recipe.InstructionDto;

@Entity
@Table(name = "recipe_instructions")
public class Instruction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int stepNo;

    @Column(nullable = false)
    private String stepDescription;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public InstructionDto toDto() {
        return new InstructionDto(getStepDescription());
    }

}
