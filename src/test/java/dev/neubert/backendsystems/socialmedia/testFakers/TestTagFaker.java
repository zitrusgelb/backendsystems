package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.*;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTagFaker {

    private TagFaker tagFaker;

    @BeforeEach
    void setup(){
        tagFaker = new TagFaker();
    }

    @Test
    void testOneTag(){
        Tag tag = tagFaker.createModel();
        assertNotNull(tag.getName());
    }

}
