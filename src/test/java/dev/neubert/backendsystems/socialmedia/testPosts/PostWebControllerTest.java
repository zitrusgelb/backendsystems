package dev.neubert.backendsystems.socialmedia.testPosts;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PostWebControllerTest {

    @Test
    void getAllPostsNoPostsExisting() {
        given().when()
               .get("/posts")
               .then()
               .statusCode(200)
               .header("X-Total-Count", "0")
               .header("content-length", "2");
    }

    @Test
    void createPost() {
        Post post = new PostFaker().createModel();
        post.setCreatedAt(LocalDateTime.now());
        String postToString = post.toString();
        given().contentType("application/json")
               .body(post.toString())
               .when()
               .post("/posts")
               .then()
               .statusCode(201)
               .header("Location", "http://localhost:8080/posts/1");
    }
}
