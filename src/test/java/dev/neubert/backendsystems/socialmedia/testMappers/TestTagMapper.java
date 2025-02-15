package dev.neubert.backendsystems.socialmedia.testMappers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TestTagMapper {

    @Inject
    TagMapper tagMapper;

    @Test
    public void testTagToTagDto() {
        List<Post> posts = new ArrayList<>();
        Tag tag = new Tag("Test tag", posts);
        tag.setId(1);

        TagDto tagDto = tagMapper.tagToTagDto(tag);

        assertEquals(tag.getId(), tagDto.getId());
        assertEquals(tag.getName(), tagDto.getName());
        assertEquals(tag.getPosts().size(), tagDto.getPosts().size());
    }

    @Test
    public void testTagDtoToTag() {
        List<PostDto> posts = new ArrayList<>();
        TagDto tagDto = new TagDto("Test tag", posts);
        tagDto.setId(1);

        Tag tag = tagMapper.tagDtoToTag(tagDto);

        assertEquals(tagDto.getId(), tag.getId());
        assertEquals(tagDto.getName(), tag.getName());
        assertEquals(tagDto.getPosts().size(), tag.getPosts().size());
    }

    @Test
    public void testTagToTagEntity() {
        List<Post> posts = new ArrayList<>();
        Tag tag = new Tag("Test tag", posts);
        tag.setId(1);

        TagEntity tagEntity = tagMapper.tagToTagEntity(tag);

        assertEquals(tag.getId(), tagEntity.getId());
        assertEquals(tag.getName(), tagEntity.getName());
        assertEquals(tag.getPosts().size(), tagEntity.getPosts().size());
    }

    @Test
    public void testTagEntityToTag() {
        List<PostEntity> posts = new ArrayList<>();
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(1);
        tagEntity.setPosts(posts);
        tagEntity.setName("Test tag");

        Tag tag = tagMapper.tagEntityToTag(tagEntity);

        assertEquals(tagEntity.getId(), tag.getId());
        assertEquals(tagEntity.getName(), tag.getName());
        assertEquals(tagEntity.getPosts().size(), tag.getPosts().size());
    }
}

