package dev.neubert.backendsystems.socialmedia.application.port.out.Post;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

public interface ReadPostOut {
    Post getPostById(long id);
}
