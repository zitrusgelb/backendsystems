package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.LikeFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TestLikeFaker {
    @Inject
    LikeFaker likeFaker;

    @Test
    void testOneLike() {
        Like like = likeFaker.createModel();
        assertNotNull(like.getPost());
        assertNotNull(like.getPost());
        assertNotNull(like.getTimestamp());
    }

}
