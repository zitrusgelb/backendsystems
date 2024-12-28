package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.util.List;

public interface ReadAllUsersOut {
    List<User> getAllUsers(int limit, int offset);

    List<User> getAllUsers(int limit);
}
