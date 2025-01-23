package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.PostRepository;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
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

    @Inject
    UserRepository userRepository;

    @Inject
    TagRepository tagRepository;

    @Override
    public Post create(Post post) {
        if (post.getUser().getId() == 0) {
            var user = userRepository.getUser(post.getUser().getUsername());
            post.setUser(user);
        }
        if (post.getTag().getName() != null && !post.getTag().getName().isEmpty()) {
            post.setTag(post.getTag()); // TODO: use tagRepository as soon as it is implemented
        } else {
            post.setTag(null);
        }
        if (post.getReplyTo().getId() != 0) {
            post.setReplyTo(postRepository.getPostById(post.getReplyTo().getId()));
        } else {
            post.setReplyTo(null);
        }
        return postRepository.createPost(post);
    }

    @Override
    public boolean delete(Post post) {
        return postRepository.deletePost(post.getId());
    }

    @Override
    public List<Post> readAllPosts() {
        return postRepository.readAllPosts(100); // TODO: set correct limit!
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.getPostById(id);
    }

    @Override
    public Post updatePost(long postId, Post post) {
        return postRepository.updatePost(post);
    }
}
