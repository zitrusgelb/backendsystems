package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService implements CreateUserIn, ReadAllUsersIn, ReadUserIn {

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
}