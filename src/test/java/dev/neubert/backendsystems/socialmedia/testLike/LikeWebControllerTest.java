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
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusTest
public class LikeWebControllerTest {

    @Inject
    PostService postService;

    @Inject
    UserService userService;

    @Inject
    LikeService likeService;

    @Inject
    PostFaker postFaker;

    @Inject
    UserFaker userFaker;

    @Inject
    LikeFaker likeFaker;

    @Test
    public void testCreateLikeValid() {
        Post post = postFaker.createModel();
        post = postService.create(post);

        User user = userFaker.createModel();
        user = userService.createUser(user);

        RestAssured.given()
                   .pathParam("id", post.getId())
                   .contentType("application/json")
                   .header("X-User-Id", user.getId())
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201)
                   .body("post.id", equalTo((int) (post.getId())))
                   .body("user.id", equalTo((int) (user.getId())));
    }

    @Test
    public void testCreateLikeInvalidPostId() {
        User user = userFaker.createModel();
        user = userService.createUser(user);

        RestAssured.given()
                   .pathParam("id", Integer.MIN_VALUE)
                   .contentType("application/json")
                   .header("X-User-Id", user.getId())
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(404);
    }

    @Test
    public void testCreateLikeInvalidUserId() {
        Post post = postFaker.createModel();
        post = postService.create(post);

        RestAssured.given()
                   .pathParam("id", post.getId())
                   .contentType("application/json")
                   .header("X-User-Id", Integer.MAX_VALUE)
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(400);
    }

    @Test
    public void testDeleteLikeNonExistingLike() {
        Post post = postFaker.createModel();
        post = postService.create(post);
        User user = userFaker.createModel();
        user = userService.createUser(user);

        RestAssured.given()
                   .pathParam("id", post.getId())
                   .contentType("application/json")
                   .header("X-User-Id", user.getId())
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }

    @Test
    public void testDeleteLikeExistingLike() {
        Like like = likeFaker.createModel();
        like = likeService.create(like);

        RestAssured.given()
                   .pathParam("id", like.getPost().getId())
                   .contentType("application/json")
                   .header("X-User-Id", like.getUser().getId())
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }

    @Test
    public void testGetLikesByPostNonExistentPost() {
        User user = userFaker.createModel();
        user = userService.createUser(user);

        RestAssured.given()
                   .pathParam("id", "99")
                   .contentType("application/json")
                   .header("X-User-Id", user.getId())
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(400);
    }

    @Test
    public void testGetLikesByPostNonExistingLikes() {
        Post post1 = postFaker.createModel();
        post1 = postService.create(post1);

        RestAssured.given()
                   .pathParam("id", post1.getId())
                   .contentType("application/json")
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "0")
                   .body("size()", equalTo(0));
    }

    @Test
    public void testGetLikesByPost() {
        Post post1 = postFaker.createModel();
        post1 = postService.create(post1);
        Post post2 = postFaker.createModel();
        post2 = postService.create(post2);

        User user1 = userFaker.createModel();
        user1 = userService.createUser(user1);
        User user2 = userFaker.createModel();
        user2 = userService.createUser(user2);

        Like like1 = new Like(post1, user1, LocalDateTime.now());
        Like like2 = new Like(post1, user2, LocalDateTime.now());
        Like like3 = new Like(post2, user1, LocalDateTime.now());

        likeService.create(like1);
        likeService.create(like2);
        likeService.create(like3);

        RestAssured.given()
                   .pathParam("id", post1.getId())
                   .contentType("application/json")
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "2")
                   .header("Cache-Control", equalTo("no-transform, max-age=300"))
                   .body("size()", equalTo(2))
                   .body("[0].user.id", equalTo((int) (user1.getId())))
                   .body("[0].post.id", equalTo((int) (post1.getId())))
                   .body("[1].user.id", equalTo((int) (user2.getId())));
    }


    @Test
    public void testGetLikesByUserNonExistentUser() {
        RestAssured.given()
                   .pathParam("username", "no_existent_user")
                   .contentType("application/json")
                   .header("X-User-Id", 99)
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(404);
    }

    @Test
    public void testGetLikesByUserNonExistingLikes() {
        User user = userFaker.createModel();
        user = userService.createUser(user);

        RestAssured.given()
                   .pathParam("username", user.getUsername())
                   .contentType("application/json")
                   .header("X-User-Id", user.getId())
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "0")
                   .body("size()", equalTo(0));
    }

    @Test
    public void testGetLikesByUser() {
        Post post1 = postFaker.createModel();
        post1 = postService.create(post1);
        Post post2 = postFaker.createModel();
        post2 = postService.create(post2);

        User user1 = userFaker.createModel();
        user1 = userService.createUser(user1);
        User user2 = userFaker.createModel();
        user2 = userService.createUser(user2);

        Like like1 = new Like(post1, user1, LocalDateTime.now());
        Like like2 = new Like(post1, user2, LocalDateTime.now());
        Like like3 = new Like(post2, user1, LocalDateTime.now());

        likeService.create(like1);
        likeService.create(like2);
        likeService.create(like3);

        RestAssured.given()
                   .pathParam("username", user1.getUsername())
                   .contentType("application/json")
                   .header("X-User-Id", user1.getId())
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "2")
                   .header("Cache-Control", equalTo("no-transform, max-age=300"))
                   .body("size()", equalTo(2))
                   .body("[0].user.id", equalTo((int) (user1.getId())))
                   .body("[0].post.id", equalTo((int) (post1.getId())))
                   .body("[1].post.id", equalTo((int) (post2.getId())));
    }
}
