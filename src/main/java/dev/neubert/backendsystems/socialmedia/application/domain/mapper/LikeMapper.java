package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.LikeEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("LikeMapper")
@Mapper(componentModel = "jakarta-cdi",
        uses = {PostMapper.class, UserMapper.class, TagMapper.class})
public interface LikeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post.user", qualifiedByName = {"UserMapper", "UserDtoWithoutNested"})
    LikeDto likeToLikeDto(Like like);

    @IterableMapping(qualifiedByName = "LikeDtoWithoutNested")
    List<LikeDto> likeToLikeDto(List<Like> like);

    @Mapping(target = "id", ignore = true)
    Like likeDtoToLike(LikeDto like);

    LikeEntity likeToLikeEntity(Like like);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", qualifiedByName = {"UserMapper", "UserWithoutNested"})
    @Mapping(target = "post.user", qualifiedByName = {"UserMapper", "UserWithoutNested"})
    Like likeEntityToLike(LikeEntity like);

    @Named("LikeListWithoutNested")
    @IterableMapping(qualifiedByName = "LikeWithoutNested")
    List<Like> likeEntityToLikeList(List<LikeEntity> likeEntities);

    @Named("LikeWithoutNested")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", qualifiedByName = {"UserMapper", "UserWithoutNested"})
    @Mapping(target = "post", qualifiedByName = {"PostMapper", "PostWithoutNested"})
    Like withoutNested(LikeEntity like);

    @Named("LikeDtoWithoutNested")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", qualifiedByName = {"UserMapper", "UserDtoWithoutNested"})
    @Mapping(target = "post", qualifiedByName = {"PostMapper", "PostDtoWithoutNested"})
    LikeDto withoutNested(Like like);

}