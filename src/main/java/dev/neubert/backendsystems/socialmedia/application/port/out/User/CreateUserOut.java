package dev.neubert.backendsystems.socialmedia.application.port.out.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import org.jboss.resteasy.util.NoContent;

public interface CreateUserOut {
    NoContent createUser(User user);
}
