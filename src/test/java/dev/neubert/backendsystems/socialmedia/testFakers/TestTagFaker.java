package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TestTagFaker {

    @Inject
    TagFaker tagFaker;

    @Test
    void testOneTag() {
        var tag = tagFaker.createModel();
        assertNotNull(tag);
    }

}
