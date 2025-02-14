package dev.neubert.backendsystems.socialmedia.testUsers;

import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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

    static User user = null;

    @Test
    @Order(0)
    void testReadNoUser() {
        var users = readAllUsersIn.getAllUsers(1, 0);
        assert users.isEmpty();
    }

    @Test
    @Order(1)
    void testCreateUser() {
        user = userFaker.createModel();

        var createdUser = createUserIn.createUser(user);
        assert createdUser.getUsername().equals(user.getUsername());
        assert createdUser.getDisplayName().equals(user.getDisplayName());
    }

    @Test
    @Order(2)
    void testReadAllUsers() {
        var users = readAllUsersIn.getAllUsers(10, 0);
        assert users.size() == 1;
        assert users.getFirst().getUsername().equals(user.getUsername());
        assert users.getFirst().getDisplayName().equals(user.getDisplayName());
    }

    @Test
    @Order(2)
    void testReadUserByUserName() {
        var users = readUserIn.getUser(user.getUsername());
        assert users.getUsername().equals(user.getUsername());
        assert users.getDisplayName().equals(user.getDisplayName());
    }
}
