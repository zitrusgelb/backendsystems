package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.*;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestLikeFaker {
    private LikeFaker likeFaker;

    @BeforeEach
    void setup(){
        likeFaker = new LikeFaker();
    }

    @Test
    void testOneLike(){
        Like like = likeFaker.createModel();
        assertNotNull(like.getPost());
        assertNotNull(like.getPost());
        assertNotNull(like.getTimestamp());
    }

}
