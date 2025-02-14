package dev.neubert.backendsystems.socialmedia.testLike;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@QuarkusIntegrationTest
public class LikeWebControllerIT {

    @Test
    public void testCreateLike() {
        int postId = createPost();

        given().pathParam("id", postId)
               .contentType("application/json")
               .header("X-Integration-Test", "true")
               .when()
               .post("/posts/{id}/likes")
               .then()
               .statusCode(201)
               .body("post.id", equalTo(postId));

    }

    @Test
    public void testCreateLikeInvalidPostId() {

        RestAssured.given()
                   .pathParam("id", Integer.MIN_VALUE)
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .when()
                   .post("/posts/{id}/likes")
                   .then()
                   .statusCode(404);
    }


    @Test
    public void testDeleteLikeNonExistingLike() {

        RestAssured.given()
                   .pathParam("id", createPost())
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }

    @Test
    public void testDeleteLikeExistingLike() {
        int postId = createPost();
        int userId = createLike(postId);

        RestAssured.given()
                   .pathParam("id", postId)
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .header("X-User-Id", userId)
                   .when()
                   .delete("/posts/{id}/likes")
                   .then()
                   .statusCode(204);
    }

    @Test
    public void testGetLikesByPostNonExistentPost() {
        RestAssured.given()
                   .pathParam("id", Integer.MAX_VALUE)
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(400);
    }

    @Test
    public void testGetLikesByPostNonExistingLikes() {
        RestAssured.given()
                   .pathParam("id", createPost())
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "0")
                   .body("size()", equalTo(0));
    }

    @Test
    public void testGetLikesByPost() {
        int postId1 = createPost();

        int userId1 = createLike(postId1);
        int userId2 = createLike(postId1);

        RestAssured.given()
                   .pathParam("id", postId1)
                   .contentType("application/json")
                   .header("X-Integration-Test", "true")
                   .when()
                   .get("/posts/{id}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "2")
                   .header("Cache-Control", equalTo("no-transform, max-age=300"))
                   .body("size()", equalTo(2))
                   .body("[0].user.id", equalTo(userId1))
                   .body("[0].post.id", equalTo(postId1))
                   .body("[1].user.id", equalTo(userId2));
    }


    @Test
    public void testGetLikesByUserNonExistentUser() {
        RestAssured.given()
                   .pathParam("username", "no_existent_user")
                   .header("X-Integration-Test", "true")
                   .contentType("application/json")
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(404);
    }

    @Test
    public void testGetLikesByUserNonExistingLikes() {
        int postId1 = createPost();

        PostDto post = RestAssured.given()
                                  .pathParam("id", postId1)
                                  .contentType("application/json")
                                  .header("X-Integration-Test", "true")
                                  .when()
                                  .get("/posts/{id}")
                                  .then()
                                  .statusCode(200)
                                  .extract()
                                  .body()
                                  .as(PostDto.class);

        RestAssured.given()
                   .pathParam("username", post.getUser().getUsername())
                   .header("X-Integration-Test", "true")
                   .header("X-User-Id", post.getUser().getId())
                   .contentType("application/json")
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "0")
                   .body("size()", equalTo(0));
    }

    @Test
    public void testGetLikesByUser() {
        int postId1 = createPost();

        int userId1 = createLike(postId1);

        String username = given().when()
                                 .header("X-User-Id", userId1)
                                 .header("X-Integration-Test", "true")
                                 .get("/users/me")
                                 .then()
                                 .assertThat()
                                 .statusCode(200)
                                 .extract()
                                 .body()
                                 .as(UserDto.class)
                                 .getUsername();

        RestAssured.given()
                   .pathParam("username", username)
                   .header("X-Integration-Test", "true")
                   .contentType("application/json")
                   .header("X-User-Id", userId1)
                   .when()
                   .get("/users/{username}/likes")
                   .then()
                   .statusCode(200)
                   .header("X-Total-Count", "1")
                   .header("Cache-Control", equalTo("no-transform, max-age=300"))
                   .body("size()", equalTo(1))
                   .body("[0].user.id", equalTo(userId1))
                   .body("[0].post.id", equalTo(postId1));
    }

    private int createPost() {
        String locationHeader =
                given().contentType(ContentType.JSON)
                       .header("X-Integration-Test", "true")
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
                       .statusCode(201)
                       .header("Cache-Control", "no-transform, max-age=300")
                       .extract()
                       .header("Location");

        locationHeader = locationHeader.substring(locationHeader.length() - 1);
        return Integer.parseInt(locationHeader);
    }

    private int createLike(int postId) {
        LikeDto like = given().pathParam("id", postId)
                              .contentType("application/json")
                              .header("X-Integration-Test", "true")
                              .when()
                              .post("/posts/{id}/likes")
                              .then()
                              .statusCode(201)
                              .body("post.id", equalTo(postId))
                              .extract()
                              .body()
                              .as(LikeDto.class);

        return (int) (like.getUser().getId());
    }

}