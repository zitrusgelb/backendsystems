package dev.neubert.backendsystems.socialmedia.application.domain.mapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("UserMapper")
@Mapper(componentModel = "jakarta-cdi",
        uses = {LikeMapper.class, PostMapper.class, TagMapper.class})
public interface UserMapper {
    UserDto userToUserDto(User user);

    @IterableMapping(qualifiedByName = "UserDtoWithoutNested")
    List<UserDto> userToUserDto(List<User> users);

    User userDtoToUser(UserDto user);

    UserEntity userToUserEntity(User user);

    @Mapping(target = "posts", qualifiedByName = {"PostMapper", "PostWithoutNested"})
    @Mapping(target = "likes", qualifiedByName = {"LikeMapper", "LikeListWithoutNested"})
    User userEntityToUser(UserEntity user);

    @IterableMapping(qualifiedByName = "UserWithoutNested")
    List<User> userEntityToUser(List<UserEntity> users);

    @Named("UserDtoWithoutNested")
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "posts", ignore = true)
    UserDto dtoWithoutNested(User user);

    @Named("UserWithoutNested")
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "posts", ignore = true)
    User withoutNested(UserEntity user);
}