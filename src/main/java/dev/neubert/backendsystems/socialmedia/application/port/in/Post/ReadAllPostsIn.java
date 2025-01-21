package dev.neubert.backendsystems.socialmedia.application.port.in.Post;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

import java.util.List;

public interface ReadAllPostsIn {
    List<Post> readAllPosts ();
}
