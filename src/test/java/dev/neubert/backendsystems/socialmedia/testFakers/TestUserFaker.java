package dev.neubert.backendsystems.socialmedia.testFakers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.*;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserFaker {
    private UserFaker userFaker;

    @BeforeEach
    void setup(){
        userFaker = new UserFaker();
    }

    @Test
    void testOneUser(){
        User user  = userFaker.createModel();
        assertNotNull(user.getUsername());
        assertNotNull(user.getDisplayName());
    }

}
