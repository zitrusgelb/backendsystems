package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.jboss.resteasy.util.NoContent;

public interface UpdatePostOut {
    NoContent updatePost(Post post);
}
