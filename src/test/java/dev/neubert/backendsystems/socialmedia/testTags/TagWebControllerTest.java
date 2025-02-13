package dev.neubert.backendsystems.socialmedia.testTags;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.TagFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class TagWebControllerTest {

    @Inject
    TagFaker tagFaker;

    @Inject
    TagMapper tagMapper;

    @Test
    void getAllTagsNoTagsExisting() {
        given().when().get("/tags").then().statusCode(204);
    }

    @Test
    void getNonExistingTagShouldReturnNotFound() {
        given().when().get("/tags/9999").then().statusCode(404).body(equalTo("Tag nicht gefunden"));
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
