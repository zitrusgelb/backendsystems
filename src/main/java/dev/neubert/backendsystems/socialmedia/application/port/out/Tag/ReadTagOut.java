package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

public interface ReadTagOut {

    Tag getTagById(long id);
}