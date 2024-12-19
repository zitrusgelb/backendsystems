package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LikeFaker extends AbstractFaker implements FakerMethods<Like> {
    @Override
    public Like createModel() {
        Like like = new Like();
        Date date = faker.date().past(365, java.util.concurrent.TimeUnit.DAYS);
        LocalDateTime createdAt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        like.setTimestamp(createdAt);
        like.setUser(new UserFaker().createModel());
        like.setPost(new PostFaker().createModel());
        return like;
    }
}
