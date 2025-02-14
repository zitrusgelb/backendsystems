package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = {UserMapper.class, TagMapper.class})
public interface PostMapper {

    @Mapping(target = "tag.posts", ignore = true)
    PostDto postToPostDto(Post post);

    @Mapping(target = "tag.posts", ignore = true)
    Post postDtoToPost(PostDto postDto);

    @Mapping(target = "tag.posts", ignore = true)
    PostEntity postToPostEntity(Post post);

    @Mapping(target = "tag.posts", ignore = true)
    Post postEntityToPost(PostEntity postEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "tag.name", source = "tagName")
    @Mapping(target = "replyTo.id", source = "replyToId")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "tag.posts", ignore = true)
    Post createPostDtoToPost(CreatePostDto createPostDto);

}
