package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;

import java.util.List;

public interface ReadLikeByPostOut {
    List<Like> readLikeByPost(Post post);
}
