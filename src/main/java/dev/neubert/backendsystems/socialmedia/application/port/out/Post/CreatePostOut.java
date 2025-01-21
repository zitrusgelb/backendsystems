package dev.neubert.backendsystems.socialmedia.application.port.out.Post;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

public interface CreatePostOut {
    Post createPost(Post post);
}
