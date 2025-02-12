package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.LikeEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = {PostMapper.class, UserMapper.class})
public interface LikeMapper {

    @Mapping(target = "id", ignore = true)
    LikeDto likeToLikeDto(Like like);

    @Mapping(target = "id", ignore = true)
    Like likeDtoToLike(LikeDto like);

    LikeEntity likeToLikeEntity(Like like);

    @Mapping(target = "id", ignore = true)
    Like likeEntityToLike(LikeEntity like);
}