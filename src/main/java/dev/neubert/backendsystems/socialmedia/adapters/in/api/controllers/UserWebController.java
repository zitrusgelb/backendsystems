package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
public class UserWebController {

    @Inject
    UserAdapter userAdapter;

    @Inject
    ReadLikeByUserIn readLikeByUserIn;

    @Inject
    UserFaker userFaker;

    @Inject
    UserMapper userMapper;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers(
            @PositiveOrZero
            @DefaultValue("0")
            @QueryParam("offset")
            int offset,
            @Positive
            @DefaultValue("20")
            @QueryParam("size")
            int size
    ) {
        var users = userAdapter.getAllUsers(size, offset);
        return Response.ok(users).header("X-Total-Count", users.size()).build();
    }

    @Path("{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(
            @PathParam("username")
            String username
    ) {
        var user = userAdapter.getUserByName(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @GET
    @Path("{username}/likes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLikesByUser(
            @HeaderParam("X-User-Id")
            long userId,
            @PathParam("username")
            String username
    ) {
        if (userAdapter.getUserById(userId) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        if (userAdapter.getUserById(userId).getUsername() == username) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        var likes = readLikeByUserIn.readLikeByUser(userId);
        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", likes.size())
                       .entity(likes)
                       .build();
    }

    @POST
    @Path("populate")
    public Response populateDatabase() {
        var user = userFaker.createModel();
        userAdapter.createUser(userMapper.userToUserDto(user));

        return Response.status(HttpResponseStatus.CREATED.code()).build();
    }
}