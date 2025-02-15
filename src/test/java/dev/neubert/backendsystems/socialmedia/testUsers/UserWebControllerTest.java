package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.wildfly.common.Assert.assertTrue;

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
                          .queryParam("limit", "10")
                          .get("/users")
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


    @Test
    public void testGetNonExistentUser() {
        given().when().get("/users/9999999999").then().assertThat().statusCode(404);
    }

    @Test
    public void testGetUserInvalidUsername() {
        given().when().get("/users/invalidUsername").then().assertThat().statusCode(404);
    }

    @Test
    public void testGetPostsByUser() {
        var user = userFaker.createModel();
        user = createUserIn.createUser(user);

        given().when()
               .contentType(ContentType.JSON)
               .header("X-User-Id", user.getId())
               .body("""
                     {
                             "content": "These aren't the droids you are looking for.",
                             "tagName":"Star Wars",
                             "replyTo": null
                         }
                     """)
               .post("/posts")
               .then()
               .assertThat()
               .statusCode(201);

        given().when()
               .contentType(ContentType.JSON)
               .header("X-User-Id", user.getId())
               .body("""
                     {
                             "content": "I have a bad feeling about this...",
                             "tagName":"Star Wars",
                             "replyTo": null
                         }
                     """)
               .post("/posts")
               .then()
               .assertThat()
               .statusCode(201);

        given().contentType(ContentType.JSON)
               .when()
               .header("X-User-Id", user.getId())
               .get("/users/" + user.getUsername())
               .then()
               .assertThat()
               .statusCode(200);

        var responseBody = given().contentType(ContentType.JSON)
                                  .when()
                                  .header("X-User-Id", user.getId())
                                  .get("/users/" + user.getUsername())
                                  .getBody()
                                  .asString();

        Pattern content1 =
                Pattern.compile("\"content\":\"These aren't the droids you are looking for.\"");
        Pattern content2 = Pattern.compile("\"content\":\"I have a bad feeling about this...\"");

        assertTrue(content1.matcher(responseBody).find());
        assertTrue(content2.matcher(responseBody).find());

    }

}