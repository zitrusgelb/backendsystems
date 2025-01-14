package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class PostFaker extends AbstractFaker implements FakerMethods<Post> {
    @Override
    public Post createModel() {
        String content = faker.howIMetYourMother().quote();
        Date date = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
        LocalDateTime createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        //Tag tag = new TagFaker().createModel();
        Post post = new Post();
        UserFaker userFaker = new UserFaker();
        User user = userFaker.createModel();
        post.setContent(content.substring(0, content.length() > 250 ? 255 : content.length() - 1));
        post.setCreatedAt(createdAt);
        post.setUser(user);
        post.setTag(null);

        return post;
    }
}
