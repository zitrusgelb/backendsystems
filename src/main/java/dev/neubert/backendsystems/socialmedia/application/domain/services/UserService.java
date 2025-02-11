package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.CreateUserOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadAllUsersOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserByIdOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService implements CreateUserIn, ReadAllUsersIn, ReadUserIn, ReadUserByIdIn {

    @Inject
    CreateUserOut createUserOut;

    @Inject
    ReadAllUsersOut readAllUsersOut;

    @Inject
    ReadUserOut readUserOut;

    @Inject
    ReadUserByIdOut readUserByIdOut;

    @Override
    public User createUser(User user) {
        return createUserOut.createUser(user);
    }

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        return readAllUsersOut.getAllUsers(limit, offset);
    }

    @Override
    public List<User> getAllUsers(int limit) {
        return getAllUsers(limit, 0);
    }

    @Override
    public User getUser(String username) {
        return readUserOut.getUser(username);
    }

    @Override
    public User getUserById(long userId) {
        return readUserByIdOut.getUserById(userId);
    }
}