package dev.neubert.backendsystems.socialmedia.application.port.out.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

import java.util.List;

public interface ReadLikeByPostOut {
    List<Like> readLikeByPost(long postId);
}
