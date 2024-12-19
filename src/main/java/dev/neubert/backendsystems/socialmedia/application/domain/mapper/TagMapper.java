package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PostMapper.class})
public interface TagMapper
{
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto tagToTagDto(Tag tag);

    Tag tagDtoToTag(TagDto tag);

    TagEntity tagToTagEntity(Tag tag);

    Tag tagEntityToTag(TagEntity tag);
}
