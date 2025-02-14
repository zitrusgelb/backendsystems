package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

public interface ReadTagByNameOut {
    Tag getTagByName(String name);
}
