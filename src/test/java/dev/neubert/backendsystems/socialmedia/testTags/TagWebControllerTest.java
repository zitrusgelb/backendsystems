package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TagWebControllerTest {

    @Inject
    TagFaker tagFaker;

    @Inject
    TagMapper tagMapper;

    @Test
    void getAllTagsNoTagsExisting() {
        given().when().get("/tags").then().statusCode(200).body(is("[]"));
    }

    @Test
    void createTag() {

        given().contentType("application/json")
               .body("{\"name\": \"ThisIsATag\"}")
               .when()
               .post("/tags")
               .then()
               .statusCode(201)
               .header("Location", containsString("/tags/"));

    }
}
