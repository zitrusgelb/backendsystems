package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TagWebControllerTest {

    @Inject
    TagFaker tagFaker;

    @Inject
    TagMapper tagMapper;

    @Test
    void getAllTagsNoTagsExisting() {
        given().when()
               .get("/tags")
               .then()
               .statusCode(200)
               .body(is("[]"));
    }

    @Test
    void createTag() {
        var tag = tagFaker.createModel();
        CreateTagDto createTagDto = tagMapper.tagToCreateTagDto(tag);

        given().contentType("application/json")
               .body(createTagDto)
               .when()
               .post("/tags")
               .then()
               .statusCode(201)
               .header("Location", containsString("/tags/"));

    }
}
