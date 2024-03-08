package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByName(String name);
}
