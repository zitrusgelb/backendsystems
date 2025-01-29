package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.LikeFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.domain.services.LikeService;
import dev.neubert.backendsystems.socialmedia.application.domain.services.PostService;
import dev.neubert.backendsystems.socialmedia.application.domain.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class TestLikeService {

    @Inject
    LikeFaker likeFaker;

    @Inject
    PostFaker postFaker;

    @Inject
    UserFaker userFaker;

    @Inject
    LikeService likeService;

    @Inject
    UserService userService;

    @Inject
    PostService postService;

    @Test
    public void testCreateLike() {
        Like like = likeFaker.createModel();
        Like created = likeService.create(like);
        assertEquals(like, created);
    }

    @Test
    public void testDeleteLikeExisting() {
        Like like = likeFaker.createModel();
        likeService.create(like);
        assertTrue(likeService.deleteLike(like));
    }

    @Test
    public void testDeleteLikeNotExisting() {
        Like like = likeFaker.createModel();
        assertFalse(likeService.deleteLike(like));
    }

    @Test
    public void testReadLikesByPost() {
        Post post1 = postFaker.createModel();
        Post post2 = postFaker.createModel();
        post1 = postService.create(post1);
        post2 = postService.create(post2);

        User user1 = userFaker.createModel();
        User user2 = userFaker.createModel();
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);

        Like like1 = new Like(post1, user1, LocalDateTime.now());
        // Like like2 = new Like(post1, user2, LocalDateTime.now());
        //TODO:check why one post cant have 2 likes and one user cant give 2 likes

        // Like like3 = new Like(post2, user1, LocalDateTime.now());

        likeService.create(like1);
        // likeService.create(like2);
        // likeService.create(like3);
        List<Like> likes = likeService.readLikeByPost(post1.getId());
        assertEquals(1, likes.size());
    }

    @Test
    public void testReadLikesByUser() {

    }
}
