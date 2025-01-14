package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

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

        post.setContent(content);
        post.setCreatedAt(createdAt);
        post.setUser(new UserFaker().createModel());
        post.setTag(postTag.orElse(null));
        post.setTag(null);

        return post;
    }
}
