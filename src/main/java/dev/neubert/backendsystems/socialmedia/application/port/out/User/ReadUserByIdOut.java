package dev.neubert.backendsystems.socialmedia.application.port.out.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

public interface ReadUserByIdOut {
    User getUserById(long id);
}
