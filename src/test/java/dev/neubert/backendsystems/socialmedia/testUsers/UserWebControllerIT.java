package dev.neubert.backendsystems.socialmedia.testUsers;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
public class UserWebControllerIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void getAllUsersNoneExisting() {
        given().when()
               .get("/users")
               .then()
               .statusCode(200)
               .header("X-Total-Count", "0")
               .header("content-length", "2")
               .body(is("[]"));
    }
}