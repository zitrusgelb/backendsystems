package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@QuarkusIntegrationTest
public class TagWebControllerIT {

    private static Pattern fullLocationPattern = Pattern.compile("/posts/(\\d{1,3})");

    private int setupTestTag() {

        String postResponseHeaders =
                given().contentType(ContentType.JSON)
                       .header("X-Integration-Test", "true")
                       .body("""
                             {
                                     "content": "I am your father",
                                     "tagName": "TestTag",
                                     "replyTo": null
                                 }
                             """)
                       .when()
                       .post("/posts")
                       .headers()
                       .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group(1) : null;
        int postId = Integer.parseInt(location);

        PostDto post = given().pathParam("id", postId)
                              .contentType("application/json")
                              .header("X-Integration-Test", "true")
                              .when()
                              .get("/posts/{id}")
                              .then()
                              .statusCode(200)
                              .extract()
                              .body()
                              .as(PostDto.class);
        return (int) post.getTag().getId();
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
        int tagId = this.setupTestTag();

        given().when()
               .header("X-Integration-Test", "true")
               .get("/tags/{id}", tagId)
               .then()
               .statusCode(200)
               .body(anyOf(is(notNullValue()), containsString("Tag nicht gefunden")));
    }


    @Test
    void testUpdateTag() {
        int tagId = this.setupTestTag();

        given().contentType(ContentType.JSON)
               .header("X-Integration-Test", "true")
               .body("""
                     {
                         "name": "UpdatedTag"
                     }
                     """)
               .when()
               .put("/tags/{id}", tagId)
               .then()
               .statusCode(200)
               .body(anyOf(is(notNullValue()), containsString("Tag nicht gefunden")));
    }

    @Test
    void testDeleteTag() {
        int tagId = this.setupTestTag();

        given().header("X-Integration-Test", "true")
               .when()
               .delete("/tags/{id}", tagId)
               .then()
               .statusCode(204)
               .body(anyOf(is(""), containsString("Tag nicht gefunden")));

        given().header("X-Integration-Test", "true")
               .when()
               .get("/tags/{id}", tagId)
               .then()
               .statusCode(404)
               .body(anyOf(is(""), containsString("Tag nicht gefunden")));
    }
}
