package dev.neubert.backendsystems.socialmedia.testWebControllers.LikeWebController;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@QuarkusTest
public class LikeWebControllerTest {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    UserFaker userFaker = new UserFaker();
    LikeAdapter likeAdapter = new LikeAdapter();

    @Test
    public void testCreateLike() {
        UserDto userDto = userMapper.userToUserDto(userFaker.createModel());
        RestAssured.given()
                   .pathParam("id", 1)
                   .contentType("application/json")
                   .body(userDto)
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201);
    }

    @Test
    public void testDeleteLike() {
        UserDto userDto = userMapper.userToUserDto(userFaker.createModel());

        RestAssured.given()
                   .pathParam("id", 1)
                   .contentType("application/json")
                   .body(userDto)
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }
}
