package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mapstruct.factory.Mappers;

@Path("users")
public class UserWebController {

    @Inject
    UserAdapter userAdapter;

    @Inject
    LikeAdapter likeAdapter;

    @Inject
    UserFaker userFaker;

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

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
        return Response.ok(user).build();
    }

    @Path("{username}/likes")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLikesByUser(
            @Valid
            long userId
    ) {
        var likes = likeAdapter.getLikeByUser(userId);
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