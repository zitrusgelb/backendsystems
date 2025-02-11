package dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserAdapter {
    @Inject
    CreateUserIn createUserIn;

    @Inject
    ReadAllUsersIn readAllUsersIn;

    @Inject
    ReadUserIn readUserIn;

    @Inject
    ReadUserByIdIn readUserByIdIn;

    @Inject
    UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        var user = userMapper.userDtoToUser(userDto);
        var newUser = createUserIn.createUser(user);
        return userMapper.userToUserDto(newUser);
    }

    public List<UserDto> getAllUsers(int limit, int offset) {
        var users = readAllUsersIn.getAllUsers(limit, offset);
        var returnValue = new ArrayList<UserDto>();
        users.forEach(user -> returnValue.add(userMapper.userToUserDto(user)));
        return returnValue;
    }

    public UserDto getUserByName(String username) {
        var user = readUserIn.getUser(username);
        return userMapper.userToUserDto(user);
    }

    public UserDto getUserById(int userId) {
        var user = readUserByIdIn.getUserById(userId);
        return userMapper.userToUserDto(user);
    }
}