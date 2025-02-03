package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.UserAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.ReadPostIn;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path("posts")
public class LikerWebController {

    @Inject
    LikeAdapter likeAdapter;

    @Inject
    ReadPostIn readPostIn;

    @Inject
    PostMapper postMapper;

    @Inject
    UserAdapter userAdapter;

    @POST
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createLike(
            @HeaderParam("X-User-Id")
            long userId,
            @PathParam("id")
            long postId
    ) {
        if (readPostIn.getPostById(postId) == null || userAdapter.getUserById(userId) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        LikeDto returnValue = likeAdapter.createLike(getLikeDto(postId, userId));

        if (returnValue == null) {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
        return Response.status(HttpResponseStatus.CREATED.code()).entity(returnValue).build();
    }

    @DELETE
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteLike(
            @HeaderParam("X-User-Id")
            long userId,
            @PathParam("id")
            long postId
    ) {
        likeAdapter.deleteLike(getLikeDto(postId, userId));
        return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
    }

    @GET
    @Path("{id}/likes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLikesByPost(
            @PathParam("id")
            long id
    ) {
        if (readPostIn.getPostById(id) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        var likes = likeAdapter.getLikeByPost(id);
        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", likes.size())
                       .entity(likes)
                       .build();
    }

    private LikeDto getLikeDto(long postId, long userId) {
        PostDto postDto = postMapper.postToPostDto(readPostIn.getPostById(postId));
        UserDto userDto = userAdapter.getUserById(userId);
        return new LikeDto(postDto, userDto, LocalDateTime.now());
    }
}
