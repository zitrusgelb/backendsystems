package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = {PostMapper.class})
public interface TagMapper {

    TagDto tagToTagDto(Tag tag);

    Tag tagDtoToTag(TagDto tag);

    TagEntity tagToTagEntity(Tag tag);

    @Mapping(target = "posts", qualifiedByName = {"PostMapper", "PostEntityListToPost"})
    Tag tagEntityToTag(TagEntity tag);
}
