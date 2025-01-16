package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.CreateTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.DeleteTagIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadTagByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.ReadAllTagsIn;

import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TagService implements CreateTagIn, ReadAllTagsIn, ReadTagIn, UpdateTagIn, DeleteTagIn {

    @Inject
    TagRepository tagRepository;

    @Override
<<<<<<< HEAD
    public Tag createTag(Tag tag) {
        return tagRepository.createTag(tag);
    }


    @Override
    public boolean deleteTag(Tag tag) {
        return tagRepository.deleteTag(tag.getId());
=======
    public Tag createTag(Tag tag) { // Fixed method name to match the interface
        // Uncomment and implement the repository method
        // return tagRepository.createTag(tag);
        return null; // Placeholder until the repository method is implemented
    }

    @Override
    public boolean deleteTagIn(Long id) { // Corrected method signature if required
        // Uncomment and implement the repository method
        // return tagRepository.deleteTag(id);
        return false; // Placeholder
    }

    @Override
    public Optional<Tag> readTagById(Long id) {
        return tagRepository.readTagById(id); // Ensure the repository method is implemented
>>>>>>> 204bd47 (fix: CreateTagIn, TagService)
    }

    public List<Tag> readAllTags() {
<<<<<<< HEAD
        return tagRepository.readAllTags(100);
}
    @Override
    public Tag getTagById(long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag updateTag(Long id,Tag tag) {
        tag.setId(id);
        return tagRepository.updateTag(tag);
=======
        return tagRepository.readAllTags(); // Ensure the repository method is implemented
>>>>>>> 204bd47 (fix: CreateTagIn, TagService)
    }
}