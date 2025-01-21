package dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mapstruct.factory.Mappers;

import java.util.List;

@ApplicationScoped
public class PostAdapter {
    @Inject
    CreatePostIn createPostIn;

    @Inject
    DeletePostIn deletePostIn;

    @Inject
    ReadAllPostsIn readAllPostsIn;

    @Inject
    ReadPostIn readPostIn;

    @Inject
    UpdatePostIn updatePostIn;

    PostMapper postMapper = Mappers.getMapper(PostMapper.class);


    public PostDto createPost(CreatePostDto postDto) {
        return postMapper.postToPostDto(
                createPostIn.create(postMapper.createPostDtoToPost(postDto)));
    }

    public boolean deletePost(PostDto postDto) {
        return deletePostIn.delete(postMapper.postDtoToPost(postDto));
    }

    public List<PostDto> readAllPosts() {
        return postMapper.postListToPostDtoList(readAllPostsIn.readAllPosts());
    }

    public PostDto getPostById(long id) {
        return postMapper.postToPostDto(readPostIn.getPostById(id));
    }

    public PostDto updatePost(long id, PostDto postDto) {
        return postMapper.postToPostDto(
                updatePostIn.updatePost(id, postMapper.postDtoToPost(postDto)));
    }


}
