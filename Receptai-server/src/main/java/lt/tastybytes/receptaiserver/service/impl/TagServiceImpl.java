package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.dto.tag.CreateTagDto;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.repository.TagRepository;
import lt.tastybytes.receptaiserver.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        return Optional.ofNullable(tagRepository.findTagByName(name));
    }

    @Override
    public Tag createTag(CreateTagDto dto) {
        var tag = new Tag(dto.name(), dto.iconName());
        tagRepository.save(tag);
        return tag;
    }
}
