package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.*;
import dev.neubert.backendsystems.socialmedia.application.port.out.Post.*;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PostService
        implements CreatePostIn, DeletePostIn, ReadAllPostsIn, ReadPostIn, UpdatePostIn {

    @Inject
    PostRepository postRepository;
    ReadPostOut readPostOut;

    @Inject
    ReadAllPostsOut readAllPostsOut;

    @Inject
    CreatePostOut createPostOut;

    @Inject
    UpdatePostOut updatePostOut;

    @Inject
    DeletePostOut deletePostOut;

    @Inject
    ReadUserOut readUserOut;

    @Override
    public Post create(Post post) {
        post.setVersion(1);
        return createPostOut.createPost(completePost);
    }

    @Override
    public boolean delete(Post post) {
        return deletePostOut.deletePost(post.getId());
    }

    @Override
    public List<Post> readAllPosts() {
        return readAllPostsOut.readAllPosts(100); // TODO: set correct limit!
    }

    @Override
    public Post getPostById(long id) {
        return readPostOut.getPostById(id);
    }

    @Override
    public Post updatePost(long postId, Post post) {
        return updatePostOut.updatePost(post);
    }

    private Post getEntitiesFromDatabase(Post post) {
        if (post.getUser().getId() == 0) {
            var user = readUserOut.getUser(post.getUser().getUsername());
            post.setUser(user);
        }
        if (post.getTag() != null && post.getTag().getName() != null &&
            !post.getTag().getName().isEmpty()) {
            post.setTag(post.getTag()); // TODO: use tagRepository as soon as it is implemented
        } else {
            post.setTag(null);
        }
        if (post.getReplyTo() != null && post.getReplyTo().getId() != 0) {
            post.setReplyTo(readPostOut.getPostById(post.getReplyTo().getId()));
        } else {
            post.setReplyTo(null);
        }
        return post;
    }
}
