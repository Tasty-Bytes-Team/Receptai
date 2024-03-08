package lt.tastybytes.receptaiserver.model.tag;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lt.tastybytes.receptaiserver.dto.tag.TagDto;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String iconName;

    public Tag() {

    }

    public Tag(@NotNull String name, @NotNull String iconName) {
        this.name = name;
        this.iconName = iconName;
    }

    public TagDto toDto() {
        return new TagDto(id, name, iconName);
    }
}
