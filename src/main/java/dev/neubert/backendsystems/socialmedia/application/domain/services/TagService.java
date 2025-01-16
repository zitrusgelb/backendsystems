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
    public Tag createTag(Tag tag) { // Fixed method name to match the interface
        // Uncomment and implement the repository method
        // return tagRepository.createTag(tag);
        return null; // Placeholder until the repository method is implemented
    }

    public boolean deleteTag(Long id) { // Corrected method name to match the interface
        // Uncomment and implement the repository method
        // return tagRepository.deleteTag(id);
        return false; // Placeholder

    @Override
    public Optional<Tag> readTagById(Long id) {
        return tagRepository.readTagById(id); // Ensure the repository method is implemented
    }

    @Override
    public List<Tag> readAllTags() {
        return tagRepository.readAllTags(); // Ensure the repository method is implemented
    }
}