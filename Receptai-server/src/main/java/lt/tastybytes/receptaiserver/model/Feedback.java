package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.feedback.ExtendedFeedbackDto;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.exception.MissingRightsException;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;

@Entity
@Table(name = "feedback")
public class Feedback implements ManageableModel  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private int rating; // TODO: will store integer values between 1 and 10

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date dateCreated;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public FeedbackDto toDto() {
        return new FeedbackDto(
                author.toPublicUserDto(),
                content,
                rating,
                dateCreated
        );
    }

    public ExtendedFeedbackDto toExtendedDto() {
        return new ExtendedFeedbackDto(
                author.toPublicUserDto(),
                content,
                rating,
                dateCreated,
                recipe.toDto()
        );
    }

    @Override
    public void assertCanBeManagedBy(@NotNull User user) throws MissingRightsException {
        if (getAuthor().getId().equals(user.getId())) {
            return;
        }

        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        throw new MissingRightsException("You are missing the required rights to manage this feedback!");
    }
}
