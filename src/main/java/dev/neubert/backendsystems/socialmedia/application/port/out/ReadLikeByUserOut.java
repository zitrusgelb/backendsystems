package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.util.List;

public interface ReadLikeByUserOut {
    List<Like> readLikeByUser(User user);
}
