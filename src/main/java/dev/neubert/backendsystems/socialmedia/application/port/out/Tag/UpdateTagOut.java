package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import org.jboss.resteasy.util.NoContent;

public interface UpdateTagOut {
    Tag updateTag(long id, Tag tag);
}
