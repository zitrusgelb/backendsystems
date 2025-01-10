package dev.neubert.backendsystems.socialmedia.application.port.out.Post;

import org.jboss.resteasy.util.NoContent;

public interface DeletePostOut {
    NoContent deletePost(long postId);
}
