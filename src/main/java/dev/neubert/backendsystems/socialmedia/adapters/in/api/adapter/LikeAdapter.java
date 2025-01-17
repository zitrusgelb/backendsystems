package dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.CreateLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.DeleteLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import jakarta.inject.Inject;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class LikeAdapter {
    @Inject
    CreateLikeIn createLikeIn;

    @Inject
    DeleteLikeIn deleteLikeIn;

    @Inject
    ReadLikeByPostIn readLikeByPostIn;

    @Inject
    ReadLikeByUserIn readLikeByUserIn;

    LikeMapper likeMapper = Mappers.getMapper(LikeMapper.class);
    PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public LikeDto createLike(LikeDto likeDto) {
        return likeMapper.likeToLikeDto(createLikeIn.create(likeMapper.likeDtoToLike(likeDto)));
    }

    public boolean deleteLike(LikeDto likeDto) {
        return deleteLikeIn.deleteLikeIn(likeMapper.likeDtoToLike(likeDto));
    }

    public List<LikeDto> getLikeByPost(PostDto postDto) {
        List<Like> likes = readLikeByPostIn.readLikeByPost(postMapper.postDtoToPost(postDto));
        return likes.stream().map(likeMapper::likeToLikeDto).collect(Collectors.toList());
    }

    public List<LikeDto> getLikeByUser(UserDto userDto) {
        List<Like> likes = readLikeByUserIn.readLikeByUser(userMapper.userDtoToUser(userDto));
        return likes.stream().map(likeMapper::likeToLikeDto).collect(Collectors.toList());
    }
}
