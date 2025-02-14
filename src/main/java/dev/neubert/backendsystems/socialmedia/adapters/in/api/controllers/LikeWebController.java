package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthorizationBinding;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.Cached;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.CreateLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.DeleteLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.ReadPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path("posts")
public class LikeWebController {

    @Inject
    CreateLikeIn createLikeIn;

    @Inject
    DeleteLikeIn deleteLikeIn;

    @Inject
    ReadLikeByPostIn readLikeByPostIn;

    @Inject
    ReadPostIn readPostIn;

    @Inject
    ReadUserByIdIn readUserByIdIn;

    @Inject
    LikeMapper likeMapper;

    @POST
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @AuthorizationBinding
    @Cached
    public Response createLike(
            @HeaderParam("X-User-Id")
            long userId,
            @PathParam("id")
            long postId
    ) {
        if (readPostIn.getPostById(postId) == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }

        if (readUserByIdIn.getUserById(userId) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        LikeDto returnValue =
                likeMapper.likeToLikeDto(createLikeIn.create(getLike(postId, userId)));

        if (returnValue == null) {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
        return Response.status(HttpResponseStatus.CREATED.code()).entity(returnValue).build();
    }

    @DELETE
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    @AuthorizationBinding
    public Response deleteLike(
            @HeaderParam("X-User-Id")
            long userId,
            @PathParam("id")
            long postId
    ) {
        deleteLikeIn.deleteLike(getLike(postId, userId));
        return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
    }

    @GET
    @Path("{id}/likes")
    @Produces({MediaType.APPLICATION_JSON})
    @Cached
    public Response getLikesByPost(
            @PathParam("id")
            long id
    ) {
        if (readPostIn.getPostById(id) == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        var returnValue = readLikeByPostIn.readLikeByPost(id);
        var dtoList = returnValue.stream().map(likeMapper::likeToLikeDto).toList();

        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", dtoList.size())
                       .entity(dtoList)
                       .build();
    }

    private Like getLike(long postId, long userId) {
        Post post = readPostIn.getPostById(postId);
        User user = readUserByIdIn.getUserById((userId));
        return new Like(post, user, LocalDateTime.now());
    }
}
