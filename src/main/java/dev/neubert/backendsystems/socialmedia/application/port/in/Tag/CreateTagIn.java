package dev.neubert.backendsystems.socialmedia.application.port.in.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

public interface CreateTagIn {
    Tag createTag(Tag tag); // Ensure this matches the method in TagService
}