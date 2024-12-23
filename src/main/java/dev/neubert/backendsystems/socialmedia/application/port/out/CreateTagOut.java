package dev.neubert.backendsystems.socialmedia.application.port.out;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.jboss.resteasy.util.NoContent;

public interface CreateTagOut {
    NoContent createTag(Tag tag);
}
