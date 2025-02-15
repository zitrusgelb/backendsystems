package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.*;
import dev.neubert.backendsystems.socialmedia.application.port.out.Post.*;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.CreateTagOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.ReadTagByNameOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PostService
        implements CreatePostIn, DeletePostIn, ReadAllPostsIn, ReadPostIn, UpdatePostIn {

    @Inject
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

    @Inject
    CreateTagOut createTagOut;

    @Inject
    ReadTagByNameOut readTagByNameOut;

    @Override
    public Post create(Post post) {
        post.setVersion(1);
        Post completePost = getEntitiesFromDatabase(post);
        return createPostOut.createPost(completePost);
    }

    @Override
    public boolean delete(Post post) {
        return deletePostOut.deletePost(post.getId());
    }

    @Override
    public List<Post> readAllPosts(String query, int offset, int limit) {
        return readAllPostsOut.readAllPosts(query, offset, limit);
    }

    @Override
    public Post getPostById(long id) {
        return readPostOut.getPostById(id);
    }

    @Override
    public Post updatePost(long postId, Post post) {
        post.setVersion(readPostOut.getPostById(postId).getVersion() + 1);
        return updatePostOut.updatePost(post);
    }

    private Post getEntitiesFromDatabase(Post post) {
        if (post.getUser().getId() == 0) {
            var user = readUserOut.getUser(post.getUser().getUsername());
            post.setUser(user);
        }
        if (post.getTag().getName() != null) {
            var existingTag = readTagByNameOut.getTagByName(post.getTag().getName());
            if (existingTag == null) {
                existingTag = createTagOut.createTag(
                        post.getTag().getName());
            }
            post.setTag(existingTag);
        }
        if (post.getReplyTo() != null && post.getReplyTo().getId() != 0) {
            post.setReplyTo(readPostOut.getPostById(post.getReplyTo().getId()));
        } else {
            post.setReplyTo(null);
        }
        return post;
    }
}
