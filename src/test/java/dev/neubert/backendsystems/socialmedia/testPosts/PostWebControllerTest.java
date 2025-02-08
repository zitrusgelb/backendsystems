package dev.neubert.backendsystems.socialmedia.testPosts;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PostWebControllerTest {

    private static Pattern fullLocationPattern;
    private static Pattern postsPath;

    @BeforeAll
    static void setup() {
        fullLocationPattern = Pattern.compile("/posts/\\d{1,3}");
        postsPath = Pattern.compile("/posts/");
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
    void testGetPost() {
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
        given().contentType(ContentType.JSON)
               .when()
               .get(location)
               .then()
               .assertThat()
               .header("Cache-Control", "no-transform, max-age=300")
               .statusCode(200);

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
               .statusCode(401); // TODO: fix 401
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
               .statusCode(400);
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
