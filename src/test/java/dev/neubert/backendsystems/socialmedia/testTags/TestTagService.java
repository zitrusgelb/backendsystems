package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.domain.services.TagService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestTagService {

    @Inject
    TagService tagService;

    @Inject
    TagFaker tagFaker;

    @Test
    void testCreateTag() {
        Tag expected = tagFaker.createModel();
        Tag created = tagService.createTag(expected);
        assertEquals(expected.getName(), created.getName());
    }

    @Test
    void testDeleteExistingTag() {
        Tag created = tagService.createTag(tagFaker.createModel());
        assertTrue(tagService.deleteTag(created));
    }

    @Test
    void testDeleteNonExistingTag() {
        assertFalse(tagService.deleteTag(tagFaker.createModel()));
    }

    @Test
    void testUpdateExistingTag() {
        Tag created = tagService.createTag(tagFaker.createModel());
        created.setName("UpdatedTag");
        Tag returnedTag = tagService.update(created.getId(), created);
        assertEquals(created.getId(), returnedTag.getId());
        assertEquals("UpdatedTag", returnedTag.getName());
    }

    @Test
    void testGetTagById() {
        Tag expected = tagService.createTag(tagFaker.createModel());
        Tag actual = tagService.getTagById(expected.getId());
        assertEquals(expected.getName(), actual.getName());
    }


}
