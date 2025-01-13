package dev.neubert.backendsystems.socialmedia.application.port.in.Post;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

public interface CreatePostIn {
    Post create(Post post);
}
