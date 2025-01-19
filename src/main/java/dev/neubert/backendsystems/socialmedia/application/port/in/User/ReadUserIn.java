package dev.neubert.backendsystems.socialmedia.application.port.in.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

public interface ReadUserIn {
    User getUser(String username);
}
