package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.PostRepository;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.UserRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class LikeFaker extends AbstractFaker implements FakerMethods<Like> {

    @Inject
    UserFaker userFaker;

    @Inject
    PostFaker postFaker;

    @Inject
    UserRepository userRepository;

    @Inject
    PostRepository postRepository;

    @Override
    public Like createModel() {
        Like like = new Like();
        Date date = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
        LocalDateTime createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        like.setTimestamp(createdAt);
        var newUser = userFaker.createModel();
        like.setUser(userRepository.createUser(newUser));
        var newPost = postFaker.createModel();
        like.setPost(postRepository.createPost(newPost));
        return like;
    }
}
