package dev.neubert.backendsystems.socialmedia.testMapper;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.LikeEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TestLikeMapper {

    private LikeMapper likeMapper;

    @BeforeEach
    public void setUp() {
        likeMapper = Mappers.getMapper(LikeMapper.class);
    }

    @Test
    public void testLikeToLikeDto() {
        User user = new User("testUser", "Test User");
        user.setId(1);
        Tag tag = new Tag("Test tag", new ArrayList<>());
        tag.setId(2);

        Post post = new Post();
        post.setId(3);
        post.setContent("Test content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        post.setTag(tag);

        Like like = new Like(post, user, LocalDateTime.now());

        LikeDto likeDto = likeMapper.likeToLikeDto(like);
        //user
        assertEquals(user.getId(), likeDto.getUser().getId());
        assertEquals(user.getUsername(), likeDto.getUser().getUsername());
        assertEquals(user.getDisplayName(), likeDto.getUser().getDisplayName());
        //post
        assertEquals(post.getId(), likeDto.getPost().getId());
        assertEquals(post.getContent(), likeDto.getPost().getContent());
        assertEquals(post.getCreatedAt(), likeDto.getPost().getCreatedAt());
        //post.user
        assertEquals(post.getUser().getId(), likeDto.getPost().getUserId());
        //post.tag
        assertEquals(post.getTag().getId(), likeDto.getPost().getTagId());
        //like
        assertEquals(like.getTimestamp(), likeDto.getTimestamp());
    }

    @Test
    public void testLikeDtoToLike() {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("testUser");
        userDto.setDisplayName("Test User");

        TagDto tagDto = new TagDto("Test tag", new ArrayList<>());
        tagDto.setId(2);

        PostDto postDto = new PostDto();
        postDto.setId(3);
        postDto.setContent("Test content");
        postDto.setCreatedAt(LocalDateTime.now());
        postDto.setUserId(userDto.getId());
        postDto.setTagId(tagDto.getId());

        LikeDto likeDto = new LikeDto(postDto, userDto, LocalDateTime.now());

        Like like = likeMapper.likeDtoToLike(likeDto);

        //user
        assertEquals(userDto.getId(), like.getUser().getId());
        assertEquals(userDto.getUsername(), like.getUser().getUsername());
        assertEquals(userDto.getDisplayName(), like.getUser().getDisplayName());
        //post
        assertEquals(postDto.getId(), like.getPost().getId());
        assertEquals(postDto.getContent(), like.getPost().getContent());
        assertEquals(postDto.getCreatedAt(), like.getPost().getCreatedAt());
        //post.user
        assertEquals(postDto.getUserId(), like.getPost().getUser().getId());
        //post.tag
        assertEquals(postDto.getTagId(), like.getPost().getTag().getId());
        //like
        assertEquals(likeDto.getTimestamp(), like.getTimestamp());
    }

    @Test
    public void testLikeToLikeEntity() {
        User user = new User("testUser", "Test User");
        user.setId(1);
        Tag tag = new Tag("Test tag", new ArrayList<>());
        tag.setId(2);

        Post post = new Post();
        post.setId(3);
        post.setContent("Test content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        post.setTag(tag);

        Like like = new Like(post, user, LocalDateTime.now());

        LikeEntity likeEntity = likeMapper.likeToLikeEntity(like);
        //user
        assertEquals(user.getId(), likeEntity.getUser().getId());
        assertEquals(user.getUsername(), likeEntity.getUser().getUsername());
        assertEquals(user.getDisplayName(), likeEntity.getUser().getDisplayName());
        //post
        assertEquals(post.getId(), likeEntity.getPost().getId());
        assertEquals(post.getContent(), likeEntity.getPost().getContent());
        assertEquals(post.getCreatedAt(), likeEntity.getPost().getCreatedAt());
        //post.user
        assertEquals(post.getUser().getId(), likeEntity.getPost().getUser().getId());
        assertEquals(post.getUser().getUsername(), likeEntity.getPost().getUser().getUsername());
        assertEquals(post.getUser().getDisplayName(),
                     likeEntity.getPost().getUser().getDisplayName());
        //post.tag
        assertEquals(post.getTag().getId(), likeEntity.getPost().getTag().getId());
        assertEquals(post.getTag().getName(), likeEntity.getPost().getTag().getName());
        //like
        assertEquals(like.getTimestamp(), likeEntity.getTimestamp());
    }

    @Test
    public void testLikeEntityToLike() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("testUser");
        userEntity.setDisplayName("Test User");

        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(2);
        tagEntity.setName("Test tag");

        PostEntity postEntity = new PostEntity();
        postEntity.setId(3);
        postEntity.setContent("Test content");
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setUser(userEntity);
        postEntity.setTag(tagEntity);

        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setPost(postEntity);
        likeEntity.setUser(userEntity);
        likeEntity.setTimestamp(LocalDateTime.now());

        Like like = likeMapper.likeEntityToLike(likeEntity);

        //user
        assertEquals(userEntity.getId(), like.getUser().getId());
        assertEquals(userEntity.getUsername(), like.getUser().getUsername());
        assertEquals(userEntity.getDisplayName(), like.getUser().getDisplayName());
        //post
        assertEquals(postEntity.getId(), like.getPost().getId());
        assertEquals(postEntity.getContent(), like.getPost().getContent());
        assertEquals(postEntity.getCreatedAt(), like.getPost().getCreatedAt());
        //post.user
        assertEquals(postEntity.getUser().getId(), like.getPost().getUser().getId());
        assertEquals(postEntity.getUser().getUsername(), like.getPost().getUser().getUsername());
        assertEquals(postEntity.getUser().getDisplayName(),
                     like.getPost().getUser().getDisplayName());
        //post.tag
        assertEquals(postEntity.getTag().getId(), like.getPost().getTag().getId());
        assertEquals(postEntity.getTag().getName(), like.getPost().getTag().getName());
        //like
        assertEquals(likeEntity.getTimestamp(), like.getTimestamp());
    }
}
