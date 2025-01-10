package dev.neubert.backendsystems.socialmedia.application.port.out.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import org.jboss.resteasy.util.NoContent;

public interface CreateLikeOut {
    NoContent createLike(Like like);
}
