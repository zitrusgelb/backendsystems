package dev.neubert.backendsystems.socialmedia.application.port.out.Post;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

import java.util.List;

public interface ReadAllPostsOut {
    List<Post> readAllPosts(String query, int offset, int limit);

    List<Post> readAllPosts(int offset);

    List<Post> readAllPosts(String query);
}
