package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.*;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPostFaker {

    private PostFaker postFaker;

    @BeforeEach
    void setup(){
        postFaker = new PostFaker();
    }

    @Test
    void testOnePost(){
        Post post = postFaker.createModel();
        assertNotNull(post.getUser());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getContent());
    }

}
