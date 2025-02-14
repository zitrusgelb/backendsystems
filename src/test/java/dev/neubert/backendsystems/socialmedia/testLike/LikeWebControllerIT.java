package dev.neubert.backendsystems.socialmedia.testLike;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
public class LikeWebControllerIT {

    @Test
    public void testCreateLike() {
        String locationHeader = given().contentType(ContentType.JSON)
                                       .header("X-Integration-Test", "true")
                                       .header("X-User-Id", 100)
                                       .body("""
                                             {
                                                     "content": "I am your father",
                                                     "tag": null,
                                                     "replyTo": null
                                                 }
                                             """)
                                       .when()
                                       .post("/posts")
                                       .then()
                                       .extract()
                                       .header("Location");

        int postId = 1;
        if (locationHeader != null) {
            locationHeader = locationHeader.substring(locationHeader.length() - 1);
            postId = Integer.parseInt(locationHeader);
        }

        RestAssured.given()
                   .pathParam("id", postId)
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .header("X-User-Id", 100)
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(201);
        // .body("post.id", equalTo((int) (post.getId())))
        // .body("user.id", equalTo((int) (user.getId())));
    }

    @Test
    public void testDeleteLikeNonExistingLike() {

    }

    @Test
    public void testDeleteLikeExistingLike() {

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


    @Test
    public void testGetLikesByUserNonExistentUser() {

    }

    @Test
    public void testGetLikesByUserNonExistingLikes() {

    }

    @Test
    public void testGetLikesByUser() {

    }

}