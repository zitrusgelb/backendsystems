package dev.neubert.backendsystems.socialmedia.testPosts;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.services.PostService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestPostService {
    @Inject
    PostService postService;

    @Inject
    PostFaker postFaker;

    @Test
    void testCreatePost() {
        Post expected = postFaker.createModel();
        Post created = postService.create(expected);
        assertEquals(expected.getContent(), created.getContent());
        assertEquals(expected.getUser().getUsername(), created.getUser().getUsername());
        assertEquals(expected.getCreatedAt(), created.getCreatedAt());
        assertNull(created.getTag());
        assertNull(created.getReplyTo());
    }

    @Test
    void testDeleteExistingPost() {
        Post created = postService.create(postFaker.createModel());
        assertTrue(postService.delete(created));
    }

    @Test
    void testDeleteNonExistingPost() {
        assertFalse(postService.delete(postFaker.createModel()));
    }

    @Test
    void testUpdateExistingPost() {
        Post created = postService.create(postFaker.createModel());
        created.setContent("This should be changed now!");
        Post returnedPost = postService.updatePost(created.getId(), created);
        assertEquals(created.getId(), returnedPost.getId());
        assertEquals(created.getContent(), returnedPost.getContent());
        assertEquals(created.getUser().getUsername(), returnedPost.getUser().getUsername());
        assertEquals(created.getCreatedAt(), returnedPost.getCreatedAt());
        assertNull(returnedPost.getTag());
        assertNull(returnedPost.getReplyTo());
    }

    @Test
    void testGetPostById() {
        Post expected = postService.create(postFaker.createModel());
        Post actual = postService.getPostById(expected.getId());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getUser().getUsername(), actual.getUser().getUsername());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertNull(actual.getTag());
        assertNull(actual.getReplyTo());
    }

    @SuppressWarnings("all")
    @Test
    void testGetAllPosts() {
        Post expected1 = postService.create(postFaker.createModel());
        Post expected2 = postService.create(postFaker.createModel());
        Post expected3 = postService.create(postFaker.createModel());
        List<Post> returnedPosts = postService.readAllPosts(null, 0, 20);
        assertNotNull(returnedPosts);

    }

}
