package dev.neubert.backendsystems.socialmedia.testTags;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusIntegrationTest
public class TagWebControllerIT {

    private void setupTestTag() {

        given().contentType(ContentType.JSON)
               .header("X-Integration-Test", "true")
               .body("""
                     {
                             "content": "TestPost for TagWebControllerIT",
                             "tag": "TestTag",
                             "replyTo": null
                         }
                     """)
               .when()
               .post("/posts")
               .then()
               .statusCode(201);
    }

    @Test
    void testGetAllTags() {
        this.setupTestTag();

        given().when()
               .header("X-Integration-Test", "true")
               .get("/tags")
               .then()
               .statusCode(200)
               .header("X-Total-Count", Integer::parseInt, greaterThanOrEqualTo(0))
               .body(is(notNullValue()));
    }

    @Test
    void testGetAllTagsWithQuery() {
        this.setupTestTag();

        given().when()
               .queryParam("q", "test")
               .header("X-Integration-Test", "true")
               .get("/tags")
               .then()
               .statusCode(200)
               .body(is(notNullValue()));
    }

    @Test
    void testGetTagById() {
        this.setupTestTag();

        given().when()
               .header("X-Integration-Test", "true")
               .get("/tags/1")
               .then()
               .statusCode(anyOf(is(200), is(404)))
               .body(anyOf(is(notNullValue()), containsString("Tag nicht gefunden")));
    }


    @Test
    void testUpdateTag() {
        this.setupTestTag();

        given().contentType(ContentType.JSON)
               .header("X-Integration-Test", "true")
               .body("""
                     {
                         "name": "UpdatedTag"
                     }
                     """)
               .when()
               .put("/tags/1")
               .then()
               .statusCode(anyOf(is(200), is(404)))
               .body(anyOf(is(notNullValue()), containsString("Tag nicht gefunden")));
    }

    @Test
    void testDeleteTag() {
        this.setupTestTag();

        given().header("X-Integration-Test", "true")
               .when()
               .delete("/tags/1")
               .then()
               .statusCode(anyOf(is(204), is(404)))
               .body(anyOf(is(""), containsString("Tag nicht gefunden")));
    }
}
