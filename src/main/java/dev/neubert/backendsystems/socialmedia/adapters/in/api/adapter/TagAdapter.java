package dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TagAdapter {
    @Inject
    CreateTagIn createTagIn;

    @Inject
    DeleteTagIn deleteTagIn;

    @Inject
    ReadAllTagsIn readAllTagsIn;

    @Inject
    ReadTagIn readTagIn;

    @Inject
    UpdateTagIn updateTagIn;

    TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    public TagDto createTag(CreateTagDto tagDto) {
        return tagMapper.tagToTagDto(createTagIn.create(tagMapper.createTagDtoToTag(tagDto)));
    }

    public boolean deleteTag(TagDto tagDto) {
        Tag tag = tagMapper.tagDtoToTag(tagDto);
        return deleteTagIn.delete(tag);
    }

    public List<TagDto> readAllTags() {
        List<Tag> tags = readAllTagsIn.readAllTags();
        return tags.stream().map(tagMapper::tagToTagDto).collect(Collectors.toList());
    }

    public TagDto getTagById(long id) {
        Tag tag = readTagIn.getTagById(id);
        return tagMapper.tagToTagDto(tag);
    }

    public TagDto updateTag(long id, TagDto tagDto) {
        return tagMapper.tagToTagDto(updateTagIn.update(id, tagMapper.tagDtoToTag(tagDto)));

    }
}