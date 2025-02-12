package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TagWebControllerTest {

    @Inject
    TagFaker tagFaker;

    @Test
    void getAllTagsNoTagsExisting() {
        given().when()
               .get("/tags")
               .then()
               .statusCode(204);
    }

    @Test
    void createTag() {
        var tag = tagFaker.createModel();
        CreateTagDto createTagDto = new CreateTagDto();
        createTagDto.setName(tag.getName());

        given().contentType("application/json")
               .body(createTagDto)
               .when()
               .post("/tags")
               .then()
               .statusCode(201)
               .header("Location", containsString("/tags/"));
    }

    @Test
    void createTagWithEmptyNameShouldReturnBadRequest() {
        CreateTagDto createTagDto = new CreateTagDto();
        createTagDto.setName("");

        given().contentType("application/json")
               .body(createTagDto)
               .when()
               .post("/tags")
               .then()
               .statusCode(400)
               .body(equalTo("Tag-Name darf nicht leer sein"));
    }

    @Test
    void getNonExistingTagShouldReturnNotFound() {
        given().when()
               .get("/tags/9999")
               .then()
               .statusCode(404)
               .body(equalTo("Tag nicht gefunden"));
    }

    @Test
    void deleteNonExistingTagShouldReturnNotFound() {
        given().when()
               .delete("/tags/9999")
               .then()
               .statusCode(404)
               .body(equalTo("Tag nicht gefunden"));
    }

    @Test
    void updateNonExistingTagShouldReturnNotFound() {
        TagDto tagDto = new TagDto();
        tagDto.setId(9999L);
        tagDto.setName("Updated Tag");

        given().contentType("application/json")
               .body(tagDto)
               .when()
               .put("/tags/9999")
               .then()
               .statusCode(404)
               .body(equalTo("Tag nicht gefunden"));
    }
}
