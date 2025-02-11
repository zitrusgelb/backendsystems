package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserFaker extends AbstractFaker implements FakerMethods<User> {
    @Inject
    ReadUserOut readUserOut;

    @Override
    public User createModel() {
        String username = faker.pokemon().name();
        while (readUserOut.getUser(username) != null) {
            username = username + faker.pokemon().name();
        }

        String displayName = faker.artist().name();

        return new User(username, displayName);
    }
}
