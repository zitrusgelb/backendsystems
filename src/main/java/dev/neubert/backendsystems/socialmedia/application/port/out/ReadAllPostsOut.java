package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

import java.util.List;

public interface ReadAllPostsOut {
    List<Post> readAllPosts(int limit, int offset);

    List<Post> readAllPosts(int limit);
}
