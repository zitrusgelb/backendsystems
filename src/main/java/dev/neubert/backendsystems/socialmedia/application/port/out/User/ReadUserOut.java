package dev.neubert.backendsystems.socialmedia.application.port.out.User;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

public interface ReadUserOut {
    User getUser(String username);
}
