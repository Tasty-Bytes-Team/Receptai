package lt.tastybytes.receptaiserver.model.recipe;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.recipe.RecipeDto;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
import lt.tastybytes.receptaiserver.model.Feedback;
import lt.tastybytes.receptaiserver.model.ManageableModel;
import lt.tastybytes.receptaiserver.model.category.Category;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.model.user.User;
import lt.tastybytes.receptaiserver.utils.Converter;
import org.hibernate.annotations.Formula;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe implements ManageableModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    // TODO: WARNING, this is sensitive to schema changes, investigate a better way in the future
    @Formula("(SELECT AVG(f.rating) FROM feedback f WHERE f.recipe_id = id)")
    private Double averageRating;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientType> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Instruction> instructions = new ArrayList<>();

    public void setCategory(Category category) {
        this.categories = new ArrayList<>();
        this.categories.add(category);
    }

    public void clearTags() {
        if (tags != null) tags.clear();
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    @ManyToMany
    @JoinTable(
            name = "recipe_categories",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(nullable = false)
    private Date dateCreated;
    private Date dateModified;

    @Column(nullable = false)
    private int minutesToPrepare;

    @Column(nullable = false)
    private int portionCount;

    public RecipeDto toDto() {
        String embedUrl = null;
        if (getTutorialVideo() != null) {
            var videoId = Converter.extractVideoIdFromUrl(getTutorialVideo());
            if (videoId.isPresent()) embedUrl = "https://www.youtube.com/embed/" + videoId.get();
        }

        var ratingForDisplay = averageRating;
        if (ratingForDisplay == null) ratingForDisplay = -1.0;

        return new RecipeDto(
                id,
                getName(),
                getDescription(),
                author.toPublicUserDto(),
                getDateCreated(),
                getDateModified(),
                getPreviewImage(),
                getTutorialVideo(),
                embedUrl,
                getIngredients().stream().map(IngredientType::toDto).toList(),
                getInstructions().stream().map(Instruction::toDto).toList(),
                getTags().stream().map(Tag::toDto).toList(),
                getCategories().stream().map(Category::toDto).toList(),
                getMinutesToPrepare(),
                getPortionCount(),
                ratingForDisplay
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

    public void clearInstructions() {
        if (instructions != null) instructions.clear();
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

    public List<Category> getCategories() {
        return categories;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void clearIngredients() {
        if (ingredients != null) ingredients.clear();
    }

    public void addIngredientType(IngredientType ingredientType) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredientType);
        ingredientType.setRecipe(this);
    }

    @Override
    public void assertCanBeManagedBy(@NotNull User user) throws MissingRightsException {
        if (getAuthor().getId().equals(user.getId())) {
            return;
        }

        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        throw new MissingRightsException("You are missing the required rights to manage this recipe!");
    }
}
