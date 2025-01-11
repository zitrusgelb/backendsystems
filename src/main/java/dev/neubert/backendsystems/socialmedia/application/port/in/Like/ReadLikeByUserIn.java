package dev.neubert.backendsystems.socialmedia.application.port.in.Like;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.util.List;

public interface ReadLikeByUserIn
{
    List<Like> readLikeByUser(User user);
}
