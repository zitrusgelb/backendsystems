package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class PostFaker extends AbstractFaker implements FakerMethods<Post> {
    @Inject
    UserFaker userFaker;

    @Inject
    UserRepository userRepository;

    @Override
    public Post createModel() {
        String content = faker.howIMetYourMother().quote();
        Date date = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
        LocalDateTime createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Post post = new Post();
        var user = userFaker.createModel();
        var newUser = userRepository.getUserById(user.getId());
        if (newUser == null) {
            newUser = userRepository.createUser(user);
        }
        post.setContent(content.substring(0, content.length() > 250 ? 255 : content.length() - 1));
        post.setCreatedAt(createdAt);
        post.setUser(newUser);
        post.setTag(null);

        return post;
    }
}
