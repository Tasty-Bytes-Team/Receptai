package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.model.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String previewImage;

    @Column(nullable = true)
    private String tutorialVideo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientType> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions = new ArrayList<>();

    /*
    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    //@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    //private List<RecipeCategory> categories;
    */

    @Column(nullable = false)
    private Date dateCreated;
    private Date dateModified;

    @Column(nullable = false)
    private int minutesToPrepare;

    @Column(nullable = false)
    private int portionCount;

    public RecipeDto toDto() {
        return new RecipeDto(
                id,
                getName(),
                getDescription(),
                author.toPublicUserDto(),
                getDateCreated(),
                getDateModified(),
                getPreviewImage(),
                getTutorialVideo(),
                getIngredients().stream().map(IngredientType::toDto).toList(),
                getInstructions().stream().map(Instruction::toDto).toList(),
                new ArrayList<>(),
                new ArrayList<>(),
                getMinutesToPrepare(),
                getPortionCount()
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String getTutorialVideo() {
        return tutorialVideo;
    }

    public void setTutorialVideo(String tutorialVideo) {
        this.tutorialVideo = tutorialVideo;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public int getMinutesToPrepare() {
        return minutesToPrepare;
    }

    public void setMinutesToPrepare(int minutesToPrepare) {
        this.minutesToPrepare = minutesToPrepare;
    }

    public int getPortionCount() {
        return portionCount;
    }

    public void setPortionCount(int portionCount) {
        this.portionCount = portionCount;
    }

    public void addInstruction(Instruction instruction) {
        if (instructions == null) {
            instructions = new ArrayList<>();
        }
        instructions.add(instruction);
        instruction.setRecipe(this);
    }

    public List<IngredientType> getIngredients() {
        return ingredients;
    }

    public void addIngredientType(IngredientType ingredientType) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredientType);
        ingredientType.setRecipe(this);
    }
}
