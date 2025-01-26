package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.PostAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.LikeRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.LikeFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
    UserFaker userFaker;

    @Inject
    LikeRepository likeRepository;

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    PostMapper postMapper = Mappers.getMapper(PostMapper.class);
    LikeMapper likeMapper = Mappers.getMapper(LikeMapper.class);

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

        List<LikeDto> likes = likeAdapter.getLikeByPost(postId);
        assertFalse(likes.isEmpty());
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
        likeAdapter.createLike(likeDto);

        System.out.println("UserId: " + like.getUser().getId());
        System.out.println("PostId: " + like.getPost().getId());

        List<LikeDto> likes = likeAdapter.getLikeByPost(postId);
        System.out.println("Size Likes: " + likes.size());
        boolean exists = false;
        for (LikeDto likeDto1 : likes) {
            System.out.println("PostId: " + likeDto1.getPost().getId());
            System.out.println("UserId: " + likeDto1.getUser().getId());
            if (likeDto1.getUser().getId() == likeDto.getUser().getId()) {
                exists = true;
            }
        }
        assertTrue(exists);

        RestAssured.given()
                   .pathParam("id", postId)
                   .contentType("application/json")
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201);
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
