package dev.neubert.backendsystems.socialmedia.application.port.in.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.util.List;

public interface ReadAllUsersIn {
    List<User> getAllUsers(int limit, int offset);

    List<User> getAllUsers(int limit);
}
