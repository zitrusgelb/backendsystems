package dev.neubert.backendsystems.socialmedia.application.port.in.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

import java.util.List;

public interface ReadLikeByPostIn {
    List<Like> readLikeByPost(long postId);
}
