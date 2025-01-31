package dev.neubert.backendsystems.socialmedia.testPosts;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
public class PostWebControllerIT {

    @Inject
    PostFaker postFaker;

    @Inject
    PostMapper postMapper;

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
               .header("content-length", "2")
               .body(is("[]"));
    }

    @Test
    void createPost() {
        var post = postFaker.createModel();
        post.setCreatedAt(LocalDateTime.now());
        CreatePostDto createPostDto =
                postMapper.postDtoToCreatePostDto(postMapper.postToPostDto(post));
        given().contentType("application/json")
               .body(createPostDto.toString())
               .when()
               .post("/posts")
               .then()
               .statusCode(201)
               .header("Location", "http://localhost:8080/posts/1");
    }

}