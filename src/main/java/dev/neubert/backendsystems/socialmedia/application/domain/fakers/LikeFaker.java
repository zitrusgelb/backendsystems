package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.port.out.Post.CreatePostOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.CreateUserOut;
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
    CreateUserOut createUserOut;

    @Inject
    CreatePostOut createPostOut;

    @Override
    public Like createModel() {
        Like like = new Like();
        Date date = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
        LocalDateTime createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        like.setTimestamp(createdAt);
        var newUser = userFaker.createModel();
        like.setUser(createUserOut.createUser(newUser));
        var newPost = postFaker.createModel();
        like.setPost(createPostOut.createPost(newPost));
        return like;
    }
}
