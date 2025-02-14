package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TagService implements CreateTagIn, ReadAllTagsIn, ReadTagIn, UpdateTagIn, DeleteTagIn {

    @Inject
    ReadTagOut readTagOut;

    @Inject
    ReadAllTagsOut readAllTagsOut;

    @Inject
    CreateTagOut createTagOut;

    @Inject
    DeleteTagOut deleteTagOut;

    @Inject
    UpdateTagOut updateTagOut;

    @Override
    public Tag createTag(String name) {
        return createTagOut.createTag(name);
    }

    @Override
    public boolean deleteTag(String name) {
        return deleteTagOut.deleteTag(name);
    }

    @Override
    public List<Tag> readAllTags(String query, int offset, int limit) {
        return readAllTagsOut.readAllTags(query, offset, limit);
    }


    @Override
    public Tag getTagById(long id) {
        return readTagOut.getTagById(id);
    }

    @Override
    public Tag update(Long id, Tag tag) {
        tag.setId(id);
        return updateTagOut.updateTag(tag);
    }
}
