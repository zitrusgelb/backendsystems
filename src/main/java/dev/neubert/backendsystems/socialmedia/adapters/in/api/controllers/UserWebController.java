package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthorizationBinding;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.UserFaker;
<<<<<<< ownUserRoute
=======
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
>>>>>>> main
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.CreateUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadAllUsersIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserIn;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.CacheControl;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
public class UserWebController {

    @Inject
    CreateUserIn createUserIn;

    @Inject
    ReadAllUsersIn readAllUsersIn;

    @Inject
    ReadUserIn readUserIn;

    @Inject
    ReadUserByIdIn readUserByIdIn;

    @Inject
    ReadLikeByUserIn readLikeByUserIn;

    @Inject
    UserFaker userFaker;

    @Inject
    LikeMapper likeMapper;

    CacheControl cacheControl;

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
        var users = readAllUsersIn.getAllUsers(size, offset);
        return Response.ok(users).header("X-Total-Count", users.size()).build();
    }

    @Path("{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(
            @PathParam("username")
            String username
    ) {
        var user = readUserIn.getUser(username);
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
<<<<<<< ownUserRoute
        if (readUserByIdIn.getUserById(userId) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
=======
        if (userAdapter.getUserById(userId) == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
>>>>>>> main
        }
        if (!readUserByIdIn.getUserById(userId).getUsername().equals(username)) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        var returnValue = readLikeByUserIn.readLikeByUser(userId);
        var dtoList = returnValue.stream().map(likeMapper::likeToLikeDto).toList();

        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", dtoList.size())
                       .cacheControl(this.getCacheControl())
                       .entity(dtoList)
                       .build();
    }

    @Path("me")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @AuthorizationBinding
    public Response getMe(
            @HeaderParam("X-User-Id")
            String userId
    ) {
        var user = readUserByIdIn.getUserById(Long.parseLong(userId));

        return Response.ok(user).build();
    }

    @POST
    @Path("populate")
    public Response populateDatabase() {
        var user = userFaker.createModel();
        createUserIn.createUser(user);

        return Response.status(HttpResponseStatus.CREATED.code()).build();
    }

    private CacheControl getCacheControl() {
        this.cacheControl = new CacheControl();
        cacheControl.setMaxAge(300);
        cacheControl.setPrivate(false);
        return cacheControl;
    }
}