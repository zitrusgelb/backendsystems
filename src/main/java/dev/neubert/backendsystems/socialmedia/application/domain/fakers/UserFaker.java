package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserFaker extends AbstractFaker implements FakerMethods<User> {
    @Inject
    UserRepository userRepository;

    @Override
    public User createModel() {
        String username = faker.pokemon().name();
        if (userRepository.getUser(username) != null) {
            username = username + "_";
        }

        String displayName = faker.artist().name();

        return new User(username, displayName);
    }
}
