package dev.neubert.backendsystems.socialmedia.application.port.in.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

public interface DeleteLikeIn
{
    boolean deleteLikeIn(Like like);
}
