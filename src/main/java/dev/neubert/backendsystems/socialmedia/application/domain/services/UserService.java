package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService implements CreateUserIn, ReadAllUsersIn, ReadUserIn, ReadUserByIdIn {

    @Inject
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        return userRepository.getAllUsers(limit, offset);
    }

    @Override
    public List<User> getAllUsers(int limit) {
        return getAllUsers(limit, 0);
    }

    @Override
    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }
}