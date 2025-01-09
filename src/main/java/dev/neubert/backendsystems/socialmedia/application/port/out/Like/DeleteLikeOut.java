package dev.neubert.backendsystems.socialmedia.application.port.out.Like;

import org.jboss.resteasy.util.NoContent;

public interface DeleteLikeOut {
    NoContent deleteLike(long id);
}
