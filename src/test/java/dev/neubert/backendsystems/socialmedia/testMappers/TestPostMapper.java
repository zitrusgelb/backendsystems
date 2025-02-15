package dev.neubert.backendsystems.socialmedia.testMappers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class TestPostMapper {

    @Inject
    PostMapper postMapper;

    @Test
    public void testPostToPostDto() {
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
        post.setReplyTo(null);

        PostDto postDto = postMapper.postToPostDto(post);
        //post
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getContent(), postDto.getContent());
        assertEquals(post.getCreatedAt(), postDto.getCreatedAt());
        assertNull(postDto.getReplyTo());
        //post.user
        assertEquals(post.getUser().getId(), postDto.getUser().getId());
        assertEquals(post.getUser().getUsername(), postDto.getUser().getUsername());
        assertEquals(post.getUser().getDisplayName(), postDto.getUser().getDisplayName());
        //post.tag
        assertEquals(post.getTag().getId(), postDto.getTag().getId());
        assertEquals(post.getTag().getName(), postDto.getTag().getName());
    }

    @Test
    public void testPostDtoToPost() {
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
        postDto.setUser(userDto);
        postDto.setTag(tagDto);
        postDto.setReplyTo(null);

        Post post = postMapper.postDtoToPost(postDto);
        //post
        assertEquals(postDto.getId(), post.getId());
        assertEquals(postDto.getContent(), post.getContent());
        assertEquals(postDto.getCreatedAt(), post.getCreatedAt());
        assertNull(post.getReplyTo());
        //post.user
        assertEquals(postDto.getUser().getId(), post.getUser().getId());
        assertEquals(postDto.getUser().getUsername(), post.getUser().getUsername());
        assertEquals(postDto.getUser().getDisplayName(), post.getUser().getDisplayName());
        //post.tag
        assertEquals(postDto.getTag().getId(), post.getTag().getId());
        assertEquals(postDto.getTag().getName(), post.getTag().getName());
    }

    @Test
    public void testPostToPostEntity() {
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
        post.setReplyTo(null);

        PostEntity postEntity = postMapper.postToPostEntity(post);
        //post
        assertEquals(post.getId(), postEntity.getId());
        assertEquals(post.getContent(), postEntity.getContent());
        assertEquals(post.getCreatedAt(), postEntity.getCreatedAt());
        assertNull(postEntity.getReplyTo());
        //post.user
        assertEquals(post.getUser().getId(), postEntity.getUser().getId());
        assertEquals(post.getUser().getUsername(), postEntity.getUser().getUsername());
        assertEquals(post.getUser().getDisplayName(), postEntity.getUser().getDisplayName());
        //post.tag
        assertEquals(post.getTag().getId(), postEntity.getTag().getId());
        assertEquals(post.getTag().getName(), postEntity.getTag().getName());
    }

    @Test
    public void testPostEntityToPost() {
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
        postEntity.setReplyTo(null);

        Post post = postMapper.postEntityToPost(postEntity);
        //post
        assertEquals(postEntity.getId(), post.getId());
        assertEquals(postEntity.getContent(), post.getContent());
        assertEquals(postEntity.getCreatedAt(), post.getCreatedAt());
        assertNull(post.getReplyTo());
        //post.user
        assertEquals(postEntity.getUser().getId(), post.getUser().getId());
        assertEquals(postEntity.getUser().getUsername(), post.getUser().getUsername());
        assertEquals(postEntity.getUser().getDisplayName(), post.getUser().getDisplayName());
        //post.tag
        assertEquals(postEntity.getTag().getId(), post.getTag().getId());
        assertEquals(postEntity.getTag().getName(), post.getTag().getName());
    }
}
