package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@QuarkusTest
public class LikeWebControllerTest {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    LikeAdapter likeAdapter = new LikeAdapter();

    @Test
    public void testCreateLikeNonExistingLike() {
        RestAssured.given()
                   .pathParam("id", 1)
                   .contentType("application/json")
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201);
    }

    @Test
    public void testCreateLikeExistingLike() {

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
