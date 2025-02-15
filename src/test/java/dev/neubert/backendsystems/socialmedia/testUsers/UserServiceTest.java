package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Ensures tests run in order
public class UserServiceTest {
    @Inject
    CreateUserIn createUserIn;

    @Inject
    ReadAllUsersIn readAllUsersIn;

    @Inject
    ReadUserIn readUserIn;

    @Inject
    UserFaker userFaker;


    @Test
    void testCreateUser() {
        var user = userFaker.createModel();
        var createdUser = createUserIn.createUser(user);
        assert createdUser.getUsername().equals(user.getUsername());
        assert createdUser.getDisplayName().equals(user.getDisplayName());
    }

    @Test
    void testReadAllUsers() {
        var user = userFaker.createModel();
        var createdUser = createUserIn.createUser(user);
        var users = readAllUsersIn.getAllUsers(10, 0);
        assert users.size() <= 10;
        assertNotNull(users.getFirst().getUsername());
        assertNotNull(users.getFirst().getDisplayName());
    }

    @Test
    void testReadUserByUserName() {
        var user = userFaker.createModel();
        var createdUser = createUserIn.createUser(user);
        var users = readUserIn.getUser(user.getUsername());
        assert users.getUsername().equals(user.getUsername());
        assert users.getDisplayName().equals(user.getDisplayName());
    }
}
