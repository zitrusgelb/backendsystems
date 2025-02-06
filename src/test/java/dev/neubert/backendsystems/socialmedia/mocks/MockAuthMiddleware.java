package dev.neubert.backendsystems.socialmedia.mocks;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthMiddleware;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;

import java.net.URISyntaxException;

@Alternative
@Priority(1)
@ApplicationScoped
public class MockAuthMiddleware extends AuthMiddleware {
    @Inject
    CreateUserIn createUserIn;

    @Inject
    UserFaker userFaker;


    public MockAuthMiddleware() throws URISyntaxException {}

    @Override
    public void filter(ContainerRequestContext requestContext) {
        var user = userFaker.createModel();
        var newUser = createUserIn.createUser(user);

        requestContext.getHeaders().add("X-User-Id", String.valueOf(newUser.getId()));
    }
}
