package dev.neubert.backendsystems.socialmedia.application.port.in.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

import java.util.List;

public interface CreateTagIn {
    Tag create(Tag tag);
}