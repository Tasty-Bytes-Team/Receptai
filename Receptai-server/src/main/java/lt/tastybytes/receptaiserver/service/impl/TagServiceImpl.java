package lt.tastybytes.receptaiserver.service.impl;

import lt.tastybytes.receptaiserver.dto.tag.CreateTagDto;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.repository.TagRepository;
import lt.tastybytes.receptaiserver.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private static final int TAGS_PER_PAGE = 20;

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Page<Tag> getTags(int pageNumber) {
        return tagRepository.findAll(PageRequest.of(pageNumber, TAGS_PER_PAGE));
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        return Optional.ofNullable(tagRepository.findTagByName(name));
    }

    @Override
    public Optional<Tag> getTagById(long id) {
        return Optional.ofNullable(tagRepository.findTagById(id));
    }

    @Override
    public Tag createTag(CreateTagDto dto) {
        var tag = new Tag(dto.name(), dto.iconName());
        tagRepository.save(tag);
        return tag;
    }
}
