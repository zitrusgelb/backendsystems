package dev.neubert.backendsystems.socialmedia.testUsers;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

@QuarkusTest
public class UserWebControllerTest {

    @Test
    void getAllUsersNoneExisting() {
        given().when()
               .get("/users")
               .then()
               .statusCode(200)
               .header("X-Total-Count", "0")
               .header("content-length", "2");
    }
}