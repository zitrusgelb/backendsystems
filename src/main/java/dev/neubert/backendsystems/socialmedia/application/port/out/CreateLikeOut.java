package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import org.jboss.resteasy.util.NoContent;

public interface CreateLikeOut {
    NoContent createLike(Like like);
}
