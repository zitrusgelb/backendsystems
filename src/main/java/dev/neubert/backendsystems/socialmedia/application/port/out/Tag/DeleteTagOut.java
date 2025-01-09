package dev.neubert.backendsystems.socialmedia.application.port.out.Tag;

import org.jboss.resteasy.util.NoContent;

public interface DeleteTagOut {
    NoContent deleteTag(long id);
}
