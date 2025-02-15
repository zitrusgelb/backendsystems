package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UserWebControllerTest {

    @Inject
    CreateUserIn createUserIn;
    @Inject
    UserFaker userFaker;

    @Test
    public void testGetAllUsers() {
        var user = userFaker.createModel();
        createUserIn.createUser(user);

        var body = given().when()
                          .get("/users?limit=10")
                          .then()
                          .assertThat()
                          .statusCode(200)
                          .extract()
                          .body()
                          .as(List.class);
        assert !body.isEmpty() && body.size() <= 10;
    }

    @Test
    public void testGetUserByName() {
        var user = userFaker.createModel();
        user = createUserIn.createUser(user);

        var body = given().when()
                          .get("/users/" + user.getUsername())
                          .then()
                          .assertThat()
                          .statusCode(200)
                          .extract()
                          .body()
                          .as(UserDto.class);

        assert user.getUsername().equals(body.getUsername());
        assert user.getDisplayName().equals(body.getDisplayName());
    }

    @Test
    public void testGetOwnUser() {
        var user = userFaker.createModel();
        user = createUserIn.createUser(user);

        var body = given().when()
                          .header("X-User-Id", user.getId())
                          .get("/users/me")
                          .then()
                          .assertThat()
                          .statusCode(200)
                          .extract()
                          .body()
                          .as(UserDto.class);

        assert user.getUsername().equals(body.getUsername());
        assert user.getDisplayName().equals(body.getDisplayName());
    }
}