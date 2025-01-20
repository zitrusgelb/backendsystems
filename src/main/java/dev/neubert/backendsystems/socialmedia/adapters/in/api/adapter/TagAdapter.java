package dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.CreateTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.DeleteTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadAllTagsIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadTagByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.UpdateTagIn;
import jakarta.inject.Inject;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class TagAdapter {

    @Inject
    CreateTagIn createTagIn;

    @Inject
    DeleteTagIn deleteTagIn;

    @Inject
    ReadAllTagsIn readAllTagsIn;

    @Inject
    ReadTagByIdIn readTagByIdIn;

    @Inject
    UpdateTagIn updateTagIn;

    TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    public TagDto createTag(TagDto tagDto) {
        return tagMapper.tagToTagDto(createTagIn.createTag(tagMapper.tagDtoToTag(tagDto)));
    }

    public boolean deleteTag(TagDto tagDto) {
        return deleteTagIn.deleteTag(tagMapper.tagDtoToTag(tagDto));
    }

    public List<TagDto> getAllTags() {
        List<Tag> tags = readAllTagsIn.readAllTags();
        return tags.stream().map(tagMapper::tagToTagDto).collect(Collectors.toList());
    }


    public TagDto updateTag(TagDto tagDto) {
        Tag updatedTag = updateTagIn.updateTag(tagDto.getId(), tagMapper.tagDtoToTag(tagDto));
        return tagMapper.tagToTagDto(updatedTag);
    }
    public TagDto getTagById(long id) {
        Tag tag = new readTagByIdIn.readTagById(id);
        return tagMapper.tagToTagDto(tag);
    }
}
