package dev.neubert.backendsystems.socialmedia.adapters.in.api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Provider
@AuthorizationBinding
public class AuthMiddleware implements ContainerRequestFilter {
    final private URI uri = new URI("https://staging.api.fiw.thws.de/auth/api/users/me");
    final private HttpClient httpClient = HttpClient.newHttpClient();
    final private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    UserAdapter userAdapter;

    @Inject
    UserMapper userMapper;

    public AuthMiddleware() throws URISyntaxException {}

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!requestContext.getHeaders().containsKey("Authorization")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        var authHeader = requestContext.getHeaders().get("Authorization").getFirst();

        if (Objects.equals(authHeader, "") || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        try {
            var tokenValid = checkToken(authHeader);

            if (tokenValid == 0) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            requestContext.getHeaders().add("X-User-Id", String.valueOf(tokenValid));
        } catch (InterruptedException e) {
            requestContext.abortWith(
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    private long checkToken(String token) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder();
        request.uri(uri);
        request.setHeader("Authorization", token);

        var result = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());

        if (result.statusCode() != 200) {
            return 0;
        }

        var body = objectMapper.readValue(result.body(), new TypeReference<THWSUserResponse>() {});

        var userName = body.cn;
        var displayName = body.firstName + " " + body.lastName;

        var existingUser = userAdapter.getUserByName(userName);

        if (existingUser != null) {
            return existingUser.getId();
        }

        var newUser = new User();
        newUser.setUsername(userName);
        newUser.setDisplayName(displayName);

        var createdUser = userAdapter.createUser(userMapper.userToUserDto(newUser));

        return createdUser.getId();
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class THWSUserResponse {
    public String cn;
    public String firstName;
    public String lastName;
}