package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

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
}