package dev.neubert.backendsystems.socialmedia.testUsers;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

@QuarkusIntegrationTest
public class UserWebControllerIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }


}