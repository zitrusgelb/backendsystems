package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.PostAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.mapstruct.factory.Mappers;

@Path("posts")
public class PostWebController {

    @Inject
    PostAdapter postAdapter;

    @Inject
    PostFaker postFaker;

    PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    private UriInfo uriInfo;
    private HttpHeaders httpHeaders;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response readAllPosts(
            @DefaultValue("")
            @QueryParam("q")
            String query,
            @PositiveOrZero
            @DefaultValue("0")
            @QueryParam("offset")
            long offset,
            @Positive
            @DefaultValue("20")
            @QueryParam("size")
            long size
    ) {
        var requestedPosts = this.postAdapter.readAllPosts();
        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", requestedPosts.size())
                       .entity(requestedPosts)
                       .build();
    }


    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(
            @Positive
            @PathParam("id")
            long id
    ) {
        var requestedPost = this.postAdapter.getPostById(id);
        return Response.ok(requestedPost).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPost(
            @Valid
            CreatePostDto model
    ) {
        var result = this.postAdapter.createPost(model);
        return Response.status(HttpResponseStatus.CREATED.code())
                       .header("Location", createLocationHeader(result))
                       .build();
    }


    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updatePost(
            @Positive
            @PathParam("id")
            long id,
            @Valid
            PostDto model
    ) {
        var result = this.postAdapter.updatePost(id, model);
        return Response.status(HttpResponseStatus.NO_CONTENT.code())
                       .header("Location", createLocationHeader(result))
                       .build();
    }


    @DELETE
    @Path("{id}")
    public Response deletePost(
            @Positive
            @PathParam("id")
            long id
    ) {
        var toBeDeleted = this.postAdapter.getPostById(id);
        if (this.postAdapter.deletePost(toBeDeleted)) {
            return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
        } else {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
    }

    @POST
    @Path("populate")
    public Response populateDatabase() {
        this.postAdapter.createPost(postMapper.postDtoToCreatePostDto(
                postMapper.postToPostDto(postFaker.createModel())));
        return Response.status(HttpResponseStatus.CREATED.code()).build();
    }


    private String createLocationHeader(PostDto model) {
        return uriInfo.getRequestUriBuilder().path(Long.toString(model.getId())).build().toString();
    }

}
