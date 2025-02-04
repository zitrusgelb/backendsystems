package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PostMapper.class})
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "name", source = "name")
    TagDto tagToTagDto(Tag tag);

    @Mapping(target = "name", source = "name")
    Tag tagDtoToTag(TagDto tagDto);

    TagEntity tagToTagEntity(Tag tag);

    Tag tagEntityToTag(TagEntity tagEntity);

    @Mapping(target = "name", source = "name")
    Tag createTagDtoToTag(CreateTagDto createTagDto);
}