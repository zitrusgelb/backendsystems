package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserFaker extends AbstractFaker implements FakerMethods<User> {
    @Override
    public User createModel() {
        String username = faker.pokemon().name();
        String displayName = faker.artist().name();

        return new User(username, displayName);
    }
}
