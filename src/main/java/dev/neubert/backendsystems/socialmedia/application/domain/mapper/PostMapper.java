package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, TagMapper.class})
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tagId", source = "tag.id")
    @Mapping(target = "replyToId", source = "replyTo.id")
    PostDto postToPostDto(Post post);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "tag.id", source = "tagId")
    @Mapping(target = "replyTo.id", source = "replyToId")
    Post postDtoToPost(PostDto postDto);

    PostEntity postToPostEntity(Post post);

    Post postEntityToPost(PostEntity postEntity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "tag.id", source = "tagId")
    @Mapping(target = "replyTo.id", source = "replyToId")
    Post createPostDtoToPost(CreatePostDto createPostDto);

    CreatePostDto postDtoToCreatePostDto(PostDto postDto);

    List<PostDto> postListToPostDtoList(List<Post> posts);
}
