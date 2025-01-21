package dev.neubert.backendsystems.socialmedia.mocks;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthMiddleware;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.mapstruct.factory.Mappers;

import java.net.URISyntaxException;

@Alternative
@Priority(1)
@ApplicationScoped
public class MockAuthMiddleware extends AuthMiddleware {
    @Inject
    UserAdapter userAdapter;

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    public MockAuthMiddleware() throws URISyntaxException {}

    @Override
    public void filter(ContainerRequestContext requestContext) {
        var user = userMapper.userToUserDto(new UserFaker().createModel());

        var newUser = userMapper.userDtoToUser(userAdapter.createUser(user));

        requestContext.getHeaders().add("X-User-Id", String.valueOf(newUser.getId()));
    }
}
