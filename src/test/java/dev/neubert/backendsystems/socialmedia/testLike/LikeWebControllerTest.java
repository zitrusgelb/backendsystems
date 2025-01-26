package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.PostAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.LikeFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class LikeWebControllerTest {

    @Inject
    PostAdapter postAdapter;

    @Inject
    LikeAdapter likeAdapter;

    @Inject
    PostFaker postFaker;

    @Inject
    LikeFaker likeFaker;

    @Inject
    PostMapper postMapper;

    @Inject
    LikeMapper likeMapper;

    @Test
    public void testCreateLikeNonExistingLike() {
        final long postId = 1;
        if (postAdapter.getPostById(postId) == null) {
            Post post = postFaker.createModel();
            post.setId(postId);
            PostDto postDto = postMapper.postToPostDto(post);
            postAdapter.createPost(postMapper.postDtoToCreatePostDto(postDto));
        }

        RestAssured.given()
                   .pathParam("id", postId)
                   .contentType("application/json")
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201);
    }

    @Test
    public void testCreateLikeExistingLike() {
        final long postId = 1;
        if (postAdapter.getPostById(postId) == null) {
            Post post = postFaker.createModel();
            post.setId(postId);
            PostDto postDto = postMapper.postToPostDto(post);
            postAdapter.createPost(postMapper.postDtoToCreatePostDto(postDto));
        }

        Like like = likeFaker.createModel();
        like.getPost().setId(postId);
        LikeDto likeDto = likeMapper.likeToLikeDto(like);
        LikeDto returnValue = likeAdapter.createLike(likeDto);
        // assertTrue(returnValue != null);
        //assertEquals(like.getPost().getId(), returnValue.getPost().getId());
        // assertEquals(like.getUser().getId(), returnValue.getUser().getId());

        RestAssured.given()
                   .pathParam("id", postId)
                   .contentType("application/json")
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(409);
    }

    @Test
    public void testDeleteLikeNonExistingLike() {
        RestAssured.given()
                   .pathParam("id", 1)
                   .contentType("application/json")
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }

    @Test
    public void testDeleteLikeExistingLike() {

    }

    @Test
    public void testGetLikesByUserNonExistentUser() {
        RestAssured.given()
                   .pathParam("username", "test")
                   .contentType("application/json")
                   .when()
                   .post("/user/{username}/likes")
                   .then()
                   .statusCode(201);
    }

    @Test
    public void testGetLikesByUserNonExistingLikes() {

    }

    @Test
    public void testGetLikesByUser() {

    }

    @Test
    public void testGetLikesByPostNonExistentPost() {

    }

    @Test
    public void testGetLikesByPostNonExistingLikes() {

    }

    @Test
    public void testGetLikesByPost() {

    }

}
