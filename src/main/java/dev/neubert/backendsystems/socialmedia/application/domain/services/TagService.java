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

public class TagService implements CreateTagIn, DeleteTagIn, ReadTagByIdIn, ReadAllTagsIn {

    @Inject
    private TagRepository tagRepository;

    @Override
    public Tag create(Tag tag) {
        // Uncomment when repository method is implemented
        // return tagRepository.createTag(tag);
        return null;
    }

    @Override
    public boolean deleteTagIn(Long id) {
        // Uncomment when repository method is implemented
        // return tagRepository.deleteTag(id);
        return false;
    }

    @Override
    public Optional<Tag> readTagById(Long id) {
        return tagRepository.readTagById(id);
    }

    @Override
    public List<Tag> readAllTags() {
        return tagRepository.readAllTags();
    }
}
