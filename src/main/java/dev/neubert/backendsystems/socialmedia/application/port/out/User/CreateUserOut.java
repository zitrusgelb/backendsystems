package dev.neubert.backendsystems.socialmedia.application.port.out.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

public interface CreateUserOut {
    User createUser(User user);
}
