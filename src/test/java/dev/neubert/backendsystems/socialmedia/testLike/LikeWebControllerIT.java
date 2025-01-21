package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
public class LikeWebControllerIT {

    LikeAdapter likeAdapter = new LikeAdapter();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void getLikesByPostNoneExisting() {
        given().when()
               .get("/posts/0")
               .then()
               .statusCode(200)
               .header("X-Total-Count", "0")
               .header("content-length", "2");
    }

}
