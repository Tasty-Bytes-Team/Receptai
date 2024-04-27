package lt.tastybytes.receptaiserver.controller;

import jakarta.validation.Valid;
import lt.tastybytes.receptaiserver.dto.ErrorResponseDto;
import lt.tastybytes.receptaiserver.dto.PagedRequestDto;
import lt.tastybytes.receptaiserver.dto.PagedResponseDto;
import lt.tastybytes.receptaiserver.dto.tag.CreateTagDto;
import lt.tastybytes.receptaiserver.model.tag.Tag;
import lt.tastybytes.receptaiserver.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping(path="/create")
    public ResponseEntity<?> createTag(@Valid @RequestBody CreateTagDto dto) {
        if (tagService.getTagByName(dto.name().strip()).isPresent()) {
            return new ResponseEntity<>(
                    new ErrorResponseDto("Tag with specified name already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }
        var newTag = tagService.createTag(dto);
        return ResponseEntity.ok(newTag.toDto());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTags(@Valid PagedRequestDto pageDto) {
        var page = tagService.getTags(pageDto.page());
        return ResponseEntity.ok(
                PagedResponseDto.of(page, Tag::toDto)
        );
    }
}
