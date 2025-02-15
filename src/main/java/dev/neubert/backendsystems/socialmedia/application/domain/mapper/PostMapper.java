package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.mapstruct.*;

import java.util.List;

@Named("PostMapper")
@Mapper(componentModel = "jakarta-cdi",
        uses = {UserMapper.class, TagMapper.class, LikeMapper.class})
public interface PostMapper {

    @Mapping(target = "tag.posts", ignore = true)
    PostDto postToPostDto(Post post);

    @Mapping(target = "tag.posts", ignore = true)
    List<PostDto> postToPostDto(List<Post> post);

    @Mapping(target = "tag.posts", ignore = true)
    Post postDtoToPost(PostDto postDto);

    @Mapping(target = "tag.posts", ignore = true)
    @Mapping(target = "user.posts", ignore = true)
    PostEntity postToPostEntity(Post post);

    @Mapping(target = "tag.posts", ignore = true)
    @Mapping(target = "user.posts", ignore = true)
    @Mapping(target = "user.likes", qualifiedByName = {"LikeMapper", "LikeListWithoutNested"})
    Post postEntityToPost(PostEntity postEntity);

    @Named("PostEntityListToPost")
    @IterableMapping(qualifiedByName = "PostWithoutNested",
                     nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    List<Post> postEntityToPost(List<PostEntity> postEntity);

    @Named("PostWithoutNested")
    @Mapping(target = "tag.posts", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "replyTo", qualifiedByName = "PostWithoutNested")
    Post postWithoutNested(PostEntity postEntity);

    @Named("PostDtoWithoutNested")
    @Mapping(target = "tag.posts", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "replyTo", qualifiedByName = "PostDtoWithoutNested")
    PostDto postDtoWithoutNested(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "tag.name", source = "tagName")
    @Mapping(target = "replyTo.id", source = "replyToId")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tag.posts", ignore = true)
    Post createPostDtoToPost(CreatePostDto createPostDto);

}
