package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String previewImage;
    @OneToOne
    private User author;



    // receptas turi daug stepu
    // stepas gali priklausyti tik vienam receptui


    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    //@JoinTable(
    //        name="users_roles",
    //        joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
    //        inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<RecipeInstruction> recipeInstructions = new ArrayList<>();


    private int minutesToPrepare;
    private int portionCount;


    // TODO: date created
    // TODO: date updated

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
