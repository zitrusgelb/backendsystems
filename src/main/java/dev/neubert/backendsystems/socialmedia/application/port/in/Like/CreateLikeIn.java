package dev.neubert.backendsystems.socialmedia.application.port.in.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

public interface CreateLikeIn
{
    Like create(Like like);
}
