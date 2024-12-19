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
        // TODO: add user and tag
        Optional<Tag> tag = Optional.of(null);
        Post post = new Post();
        post.setContent(content);
        post.setCreatedAt(createdAt);
        post.setUser(null);

        post.setTag(tag.orElse(null));
        return post;
    }
}
