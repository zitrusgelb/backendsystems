package dev.neubert.backendsystems.socialmedia.application.port.in.Tag;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

public interface DeleteTagIn {
    boolean deleteTag(String name);
}