package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TestUserFaker {
    @Inject
    UserFaker userFaker;

    @Test
    void testOneUser() {
        User user = userFaker.createModel();
        assertNotNull(user.getUsername());
        assertNotNull(user.getDisplayName());
    }

}
