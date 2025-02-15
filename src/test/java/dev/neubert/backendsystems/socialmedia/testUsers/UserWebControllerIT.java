package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.wildfly.common.Assert.assertTrue;

@QuarkusIntegrationTest
public class UserWebControllerIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testGetAllUsers() {
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
        var userName = "bearfly";

        given().when()
               .header("X-Username", userName)
               .header("X-Integration-Test", "true")
               .get("/users/me");


        var body = given().when()
                          .get("/users/" + userName)
                          .then()
                          .assertThat()
                          .statusCode(200)
                          .extract()
                          .body()
                          .as(UserDto.class);

        assert body.getUsername().equals(userName);
    }

    @Test
    public void testGetOwnUser() {
        var userName = "clarinetweb";
        var body = given().when()
                          .header("X-Username", userName)
                          .header("X-Integration-Test", "true")
                          .get("/users/me")
                          .then()
                          .assertThat()
                          .statusCode(200)
                          .extract()
                          .body()
                          .as(UserDto.class);

        assert body.getUsername().equals(userName);
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


        given().when()
               .contentType(ContentType.JSON)
               .header("X-Integration-Test", "true")
               .header("X-Username", "R2-D2")
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
               .header("X-Integration-Test", "true")
               .header("X-Username", "R2-D2")
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
               .header("X-Integration-Test", "true")
               .header("X-Username", "R2-D2")
               .get("/users/R2-D2")
               .then()
               .assertThat()
               .statusCode(200);

        var responseBody = given().contentType(ContentType.JSON)
                                  .when()
                                  .header("X-Integration-Test", "true")
                                  .header("X-Username", "R2-D2")
                                  .get("/users/R2-D2")
                                  .getBody()
                                  .asString();

        Pattern content1 =
                Pattern.compile("\"content\":\"These aren't the droids you are looking for.\"");
        Pattern content2 = Pattern.compile("\"content\":\"I have a bad feeling about this...\"");

        assertTrue(content1.matcher(responseBody).find());
        assertTrue(content2.matcher(responseBody).find());

    }
}