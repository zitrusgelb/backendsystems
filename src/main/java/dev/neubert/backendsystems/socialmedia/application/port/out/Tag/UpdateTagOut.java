package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

public interface UpdateTagOut {
    Tag updateTag(Tag tag);
}
