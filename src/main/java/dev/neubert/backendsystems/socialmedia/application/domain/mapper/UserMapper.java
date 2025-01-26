package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi", uses = {LikeMapper.class, PostMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto user);

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity user);
}