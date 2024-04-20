package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;
import lt.tastybytes.receptaiserver.dto.feedback.FeedbackDto;
import lt.tastybytes.receptaiserver.dto.user.PublicUserDto;
import lt.tastybytes.receptaiserver.model.recipe.Recipe;
import lt.tastybytes.receptaiserver.model.user.User;

import java.util.Date;

@Entity
@Table(name = "feedback")
public class Feedback {
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

    public FeedbackDto toDto() {
        return new FeedbackDto(
                author.toPublicUserDto(),
                content,
                -1
        );
    }
}
