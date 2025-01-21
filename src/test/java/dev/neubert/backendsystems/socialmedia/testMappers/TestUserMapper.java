package dev.neubert.backendsystems.socialmedia.testMappers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TestUserMapper {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testUserToUserDto() {
        User user = new User("testUser", "Test User");
        user.setId(1);

        UserDto userDto = userMapper.userToUserDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getDisplayName(), userDto.getDisplayName());
    }

    @Test
    public void testUserDtoToUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("testUser");
        userDto.setDisplayName("Test User");

        User user = userMapper.userDtoToUser(userDto);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getDisplayName(), user.getDisplayName());
    }

    @Test
    public void testUserToUserEntity() {
        User user = new User("testUser", "Test User");
        user.setId(1);

        UserEntity userEntity = userMapper.userToUserEntity(user);

        assertEquals(user.getId(), userEntity.getId());
        assertEquals(user.getUsername(), userEntity.getUsername());
        assertEquals(user.getDisplayName(), userEntity.getDisplayName());
    }

    @Test
    public void testUserEntityToUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("testUser");
        userEntity.setDisplayName("Test User");

        User user = userMapper.userEntityToUser(userEntity);

        assertEquals(userEntity.getId(), user.getId());
        assertEquals(userEntity.getUsername(), user.getUsername());
        assertEquals(userEntity.getDisplayName(), user.getDisplayName());
    }
}
