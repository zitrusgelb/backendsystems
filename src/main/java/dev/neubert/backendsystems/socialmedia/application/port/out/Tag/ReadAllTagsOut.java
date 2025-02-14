package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

import java.util.List;

public interface ReadAllTagsOut {
    List<Tag> readAllTags(String query, int offset, int limit);
}
