package lt.tastybytes.receptaiserver.service;

import lt.tastybytes.receptaiserver.dto.tag.CreateTagDto;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Page<Tag> getAllTags(int pageNumber);
    Optional<Tag> getTagByName(String name);
    Optional<Tag> getTagById(long id);
    Tag createTag(CreateTagDto dto);
}
