package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.CreateTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.DeleteTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadTagByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadAllTagsIn;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TagService implements CreateTagIn, DeleteTagIn, ReadAllTagsIn {

    @Inject
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
//        if (tag == null || tag.getName() == null || tag.getName().isEmpty()) {
//            throw new IllegalArgumentException("Tag or tag name must not be null or empty");
//        }
//        return tagRepository.create(tag);
        return null;
    }

    @Override
    public boolean deleteTag(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("Tag ID must be a positive number");
//        }
//        Optional<Tag> tag = tagRepository.findById(id);
//        if (tag.isPresent()) {
//            tagRepository.delete(tag.get());
//            return true;
//        }
        return false;
    }


    @Override
    public List<Tag> readAllTags() {
        return tagRepository.readAllTags(100);

    }
}