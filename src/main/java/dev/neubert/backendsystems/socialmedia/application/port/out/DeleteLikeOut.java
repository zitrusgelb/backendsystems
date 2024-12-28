package dev.neubert.backendsystems.socialmedia.application.port.out;

import org.jboss.resteasy.util.NoContent;

public interface DeleteLikeOut {
    NoContent deleteLike(long id);
}
