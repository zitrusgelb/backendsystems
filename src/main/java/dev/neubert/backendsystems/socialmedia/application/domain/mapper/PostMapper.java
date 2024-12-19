package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TagMapper.class})
public interface PostMapper
{
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto post);

    PostEntity postToPostEntity(Post post);

    Post postEntityToPost(PostEntity post);
}
