package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta-cdi", uses = {UserMapper.class, TagMapper.class})
public interface PostMapper {

    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);

    PostEntity postToPostEntity(Post post);

    Post postEntityToPost(PostEntity postEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "tag.id", source = "tagId")
    @Mapping(target = "replyTo.id", source = "replyToId")
    @Mapping(target = "version", ignore = true)
    Post createPostDtoToPost(CreatePostDto createPostDto);

}
