package dev.neubert.backendsystems.socialmedia.testPosts;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
public class PostWebControllerIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

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
    void createPost() throws JsonProcessingException {
        Post post = new PostFaker().createModel();
        post.setCreatedAt(LocalDateTime.now());
        given().contentType("application/json")
               .body(post.toString())
               .when()
               .post("/posts")
               .then()
               .statusCode(201)
               .header("Location", "http://localhost:8080/posts/1");
    }

}