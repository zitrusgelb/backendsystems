package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TestPostFaker {

    @Inject
    PostFaker postFaker;

    @Test
    void testOnePost() {
        Post post = postFaker.createModel();
        assertNotNull(post.getUser());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getContent());
        assertNotNull(post.getTag());
    }

}
