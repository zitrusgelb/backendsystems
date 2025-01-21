package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.CreateTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.DeleteTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadAllTagsIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadTagByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.UpdateTagIn;

import jakarta.inject.Inject;

import java.util.List;


public class TagService implements CreateTagIn, DeleteTagIn, ReadAllTagsIn, ReadTagByIdIn, UpdateTagIn {

    @Inject
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        if (tag == null || tag.getName() == null || tag.getName().isEmpty()) {
            throw new IllegalArgumentException("Tag or tag name must not be null or empty");
        }
        return tagRepository.createTag(tag);
    }

    @Override
    public boolean deleteTag(Tag tag) {
        if (tag.getId() <= 0) {
            throw new IllegalArgumentException("Tag or Tag ID must not be null, and ID must be a positive number");
        }

        if (!(tagRepository.findById(tag.getId()) == null)) {
            tagRepository.deleteTag(tag.getId());
            return true;


        }
        return false;
    }

    @Override
    public List<Tag> readAllTags() {
        return tagRepository.readAllTags(100); // Example: limit of 100 tags
    }

    @Override
    public Tag readTagById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }

        Tag tag = tagRepository.findById(id);
        if (tag == null) {
            throw new IllegalArgumentException("Tag with ID " + id + " not found");
        }
        return tag;
    }

    @Override
    public Tag updateTag(long id, Tag updatedTag) {
        if (id <= 0) {
        throw new IllegalArgumentException("ID must be a positive number");
    }
        if (updatedTag == null || updatedTag.getName() == null || updatedTag.getName().isEmpty()) {
            throw new IllegalArgumentException("Updated tag or tag name must not be null or empty");
        }

        Tag existingTag = tagRepository.findById(id);
        if (existingTag != null) {
            existingTag.setName(updatedTag.getName());
            return tagRepository.updateTag(updatedTag);
        } else {
            throw new IllegalArgumentException("Tag with ID " + id + " not found");
        }
    }
}
