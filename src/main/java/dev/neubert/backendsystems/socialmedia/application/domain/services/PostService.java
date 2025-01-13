package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.PostRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PostService
        implements CreatePostIn, DeletePostIn, ReadAllPostsIn, ReadPostIn, UpdatePostIn {

    @Inject
    PostRepository postRepository;

    @Override
    public Post create(Post post) {
        // return postRepository.createPost(post);
        return null;
    }

    @Override
    public boolean delete(Post post) {
        // return postRepository.deletePost(post.getId());
        return false;
    }

    @Override
    public List<Post> readAllPosts() {
        return postRepository.readAllPosts(100); // TODO: set correct limit!
    }

    @Override
    public Post readPost(Post post) {
        return postRepository.getPostById(post.getId());
    }

    @Override
    public Post updatePost(Post post) {
        // return postRepository.updatePost(post);
        return null;
    }
}
