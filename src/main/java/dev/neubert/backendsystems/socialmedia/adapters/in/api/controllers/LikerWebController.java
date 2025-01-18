package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.LikeAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.LikeDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.UserDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path("posts")
public class LikerWebController {

    @Inject
    LikeAdapter likeAdapter;

    @GET
    @Path("{id}/likes")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLikesByPost(
            @PathParam("id")
            long id
    ) {
        var likes = likeAdapter.getLikeByPost(id);
        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", likes.size())
                       .entity(likes)
                       .build();
    }

    @POST
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createLike(
            @Valid
            UserDto userDto,
            @PathParam("id")
            long postId
    ) {

        likeAdapter.createLike(getLikeDto(postId, userDto));
        return Response.status(HttpResponseStatus.CREATED.code()).build();
    }

    @DELETE
    @Path("{id}/likes")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteLike(
            @Valid
            UserDto userDto,
            @PathParam("id")
            long postId
    ) {
        likeAdapter.deleteLike(getLikeDto(postId, userDto));
        return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
    }


    private LikeDto getLikeDto(long postId, UserDto userDto) {
        PostDto postDto = new PostDto();
        postDto.setId(postId);
        return new LikeDto(postDto, userDto, LocalDateTime.now());
    }
}
