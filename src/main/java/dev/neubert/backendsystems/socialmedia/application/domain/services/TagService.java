package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.TagRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TagService implements CreateTagIn, ReadAllTagsIn, ReadTagIn, UpdateTagIn, DeleteTagIn {

    @Inject
    TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.createTag(tag);
    }


    @Override
    public boolean deleteTag(Tag tag) {
        return tagRepository.deleteTag(tag.getId());
    }

    @Override
    public List<Tag> readAllTags() {
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
    }
}
