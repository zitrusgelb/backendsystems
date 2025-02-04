package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta-cdi", uses = {PostMapper.class})
public interface TagMapper {

    @Mapping(target = "name", source = "name")
    TagDto tagToTagDto(Tag tag);


    Tag tagDtoToTag(TagDto tagDto);

    TagEntity tagToTagEntity(Tag tag);

    Tag tagEntityToTag(TagEntity tagEntity);

    Tag createTagDtoToTag(CreateTagDto createTagDto);
}