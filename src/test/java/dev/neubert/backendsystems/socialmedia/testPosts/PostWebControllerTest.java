package dev.neubert.backendsystems.socialmedia.testPosts;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PostWebControllerTest {

    private static Pattern fullLocationPattern;
    private static Pattern tagId;

    @BeforeAll
    static void setup() {
        fullLocationPattern = Pattern.compile("/posts/\\d{1,3}");
        tagId = Pattern.compile("\"id\": (\\d{1,3})");
    }

    @Test
    void getAllPostsNoPostsExisting() {
        given().when().get("/posts").then().statusCode(200);
    }

    @Test
    void testCreatePost() {
        given().contentType(ContentType.JSON)
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
               .assertThat()
               .statusCode(201)
               .header("Cache-Control", "no-transform, max-age=300")
               .header("content-length", "0")
               .body(is(""));
    }

    @Test
    void testCreatePostWithReply() {
        String postResponseHeaders = given().contentType(ContentType.JSON)
                                            .body("""
                                                  {
                                                          "content": "I am your father",
                                                          "tag": null,
                                                          "replyTo": null
                                                      }
                                                  """)
                                            .when()
                                            .post("/posts")
                                            .headers()
                                            .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String replyToPostId =
                given().contentType(ContentType.JSON).when().get(location).getBody().asString();
        String id =
                tagId.matcher(replyToPostId).find() ? tagId.matcher(replyToPostId).group() : null;

        String postRequest = String.format("""
                                           {
                                                   "content": "I am your father",
                                                   "tag": null,
                                                   "replyTo": %s
                                               }
                                           """, id);

        given().contentType(ContentType.JSON)
               .body(postRequest)
               .when()
               .post("/posts")
               .then()
               .assertThat()
               .header("content-length", "0")
               .statusCode(201);
    }

    @Test
    void testReplyToNonExistingPost() {
        String postResponseHeaders =
                given().contentType(ContentType.JSON)
                       .header("X-User-Id", 1)
                       .body("""
                             {
                                     "content": "I am your father",
                                     "tag": null,
                                     "replyTo": 99999
                                 }
                             """)
                       .when()
                       .post("/posts")
                       .headers()
                       .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String getResponse =
                given().contentType(ContentType.JSON).when().get(location).getBody().asString();
        System.out.println(getResponse);

        assertTrue(getResponse.contains("\"replyTo\":null"));


    }


    @Test
    void testGetPost() {
        String postResponseHeaders = given().contentType(ContentType.JSON)
                                            .body("""
                                                  {
                                                          "content": "This is where the fun begins.",
                                                          "tag": null,
                                                          "replyTo": null
                                                      }
                                                  """)
                                            .when()
                                            .post("/posts")
                                            .headers()
                                            .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;
        given().contentType(ContentType.JSON)
               .when()
               .get(location)
               .then()
               .assertThat()
               .header("Cache-Control", "no-transform, max-age=300")
               .statusCode(200);

    }

    @Test
    void testDeletePost() {
        String postResponseHeaders =
                given().contentType(ContentType.JSON)
                       .header("X-User-Id", 1)
                       .body("""
                             {
                                     "content": "I am your father",
                                     "tag": null,
                                     "replyTo": null
                                 }
                             """)
                       .when()
                       .post("/posts")
                       .headers()
                       .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String postToBeDeleted =
                given().contentType(ContentType.JSON).when().get(location).getBody().asString();

        given().contentType(ContentType.JSON)
               .header("X-User-Id", 1)
               .header("If-Match", "v1")
               .body(postToBeDeleted)
               .when()
               .delete(location)
               .then()
               .assertThat()
               .statusCode(204);
    }


    @Test
    void testDeletePostWrongUser() {
        String postResponseHeaders = given().contentType(ContentType.JSON)
                                            .body("""
                                                  {
                                                          "content": "I am your father",
                                                          "tag": null,
                                                          "replyTo": null
                                                      }
                                                  """)
                                            .when()
                                            .post("/posts")
                                            .headers()
                                            .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String postToBeDeleted =
                given().contentType(ContentType.JSON).when().get(location).getBody().asString();

        given().contentType(ContentType.JSON)
               .body(postToBeDeleted)
               .when()
               .delete(location)
               .then()
               .assertThat()
               .header("content-length", "0")
               .statusCode(401);
    }

    @Test
    void testPutPost() {
        String postResponseHeaders =
                given().contentType(ContentType.JSON)
                       .header("X-User-Id", 1)
                       .body("""
                             {
                                     "content": "Hello there!",
                                     "tag": null,
                                     "replyTo": null
                                 }
                             """)
                       .when()
                       .post("/posts")
                       .headers()
                       .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String postToBeUpdated = given().contentType(ContentType.JSON)
                                        .when()
                                        .get(location)
                                        .getBody()
                                        .asString()
                                        .replace("\"Hello there!\"",
                                                 "\"General Kenobi, you are a bold one!\"");

        given().contentType(ContentType.JSON)
               .header("X-User-Id", 1)
               .header("If-Match", "v1")
               .body(postToBeUpdated)
               .when()
               .put(location)
               .then()
               .assertThat()
               .header("Cache-Control", "no-transform, max-age=300")
               .statusCode(204);
    }

    @Test
    void testPutFailPreconditions() {
        String postResponseHeaders =
                given().contentType(ContentType.JSON)
                       .header("X-User-Id", 1)
                       .body("""
                             {
                                     "content": "Hello there!",
                                     "tag": null,
                                     "replyTo": null
                                 }
                             """)
                       .when()
                       .post("/posts")
                       .headers()
                       .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String postToBeUpdated = given().contentType(ContentType.JSON)
                                        .when()
                                        .get(location)
                                        .getBody()
                                        .asString()
                                        .replace("\"Hello there!\"",
                                                 "\"General Kenobi, you are a bold one!\"");

        given().contentType(ContentType.JSON)
               .header("X-User-Id", 1)
               .body(postToBeUpdated)
               .when()
               .put(location)
               .then()
               .assertThat()
               .header("content-length", "0")
               .statusCode(412);
    }

    @Test
    void testPutPostWrongUser() {
        String postResponseHeaders = given().contentType(ContentType.JSON)
                                            .body("""
                                                  {
                                                          "content": "I am your father",
                                                          "tag": null,
                                                          "replyTo": null
                                                      }
                                                  """)
                                            .when()
                                            .post("/posts")
                                            .headers()
                                            .toString();
        Matcher locationMatcher = fullLocationPattern.matcher(postResponseHeaders);
        String location = locationMatcher.find() ? locationMatcher.group() : null;

        String postToBeUpdated = given().contentType(ContentType.JSON)
                                        .when()
                                        .get(location)
                                        .getBody()
                                        .asString()
                                        .replace("\"I am your father\"", "\"Noooooooooooooo!\"");
        given().contentType(ContentType.JSON)
               .body(postToBeUpdated)
               .when()
               .put(location)
               .then()
               .assertThat()
               .header("content-length", "0")
               .statusCode(401);
    }

    @Test
    void testCreatePostEmptyBody() {
        given().contentType(ContentType.JSON)
               .when()
               .post("/posts")
               .then()
               .assertThat()
               .header("content-length", "0")
               .statusCode(400);
    }

    @Test
    void testDeleteNonExistentPost() {
        given().contentType(ContentType.JSON)
               .when()
               .delete("/posts/9999999999999")
               .then()
               .assertThat()
               .statusCode(404);
    }

    @Test
    void testPutNonExistentPost() {
        given().contentType(ContentType.JSON)
               .when()
               .put("/posts/9999999999999")
               .then()
               .assertThat()
               .statusCode(404);
    }

    @Test
    void testSendPostToWrongURL() {
        given().contentType(ContentType.JSON)
               .body("""
                     {
                             "content": "I am your father",
                             "tag": null,
                             "replyTo": null
                         }
                     """)
               .when()
               .post("/posts/1")
               .then()
               .assertThat()
               .statusCode(405);
    }

    @Test
    void testSendPutToWrongURL() {
        given().contentType(ContentType.JSON)
               .when()
               .post("/posts")
               .then()
               .assertThat()
               .statusCode(400);
    }
}
