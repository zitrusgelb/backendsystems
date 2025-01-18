package dev.neubert.backendsystems.socialmedia.application.port.out.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

public interface DeleteLikeOut {
    boolean deleteLike(Like like);
}
