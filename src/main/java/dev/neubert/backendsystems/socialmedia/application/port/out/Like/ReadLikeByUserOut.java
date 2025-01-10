package dev.neubert.backendsystems.socialmedia.application.port.out.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.util.List;

public interface ReadLikeByUserOut {
    List<Like> readLikeByUser(User user);
}
