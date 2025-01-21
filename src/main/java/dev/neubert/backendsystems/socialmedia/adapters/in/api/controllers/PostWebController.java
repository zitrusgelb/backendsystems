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
import jakarta.ws.rs.core.*;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Path("posts")
public class PostWebController {

    @Inject
    PostAdapter postAdapter;

    @Inject
    PostFaker postFaker;

    PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    private UriInfo uriInfo;
    private HttpHeaders httpHeaders;
    private CacheControl cacheControl;


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

        setCacheControlFiveMinutes();
        var allPosts = this.postAdapter.readAllPosts();
        var filteredPosts =
                allPosts.stream().filter(post -> post.getContent().contains(query)).toList();

        var result = filteredPosts.stream().skip(offset).limit(size).collect(Collectors.toList());

        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", result.size())
                       .cacheControl(this.cacheControl)
                       .entity(result)
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
        setCacheControlFiveMinutes();
        var requestedPost = this.postAdapter.getPostById(id);
        if (requestedPost == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        return Response.ok(requestedPost)
                       .tag(Long.toString(requestedPost.hashCode()))
                       .cacheControl(this.cacheControl)
                       .build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPost(
            @Valid
            CreatePostDto model
    ) {
        setCacheControlFiveMinutes();
        var result = this.postAdapter.createPost(model);
        return Response.status(HttpResponseStatus.CREATED.code())
                       .header("Location", createLocationHeader(result))
                       .tag(Long.toString(result.hashCode()))
                       .cacheControl(this.cacheControl)
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
        setCacheControlFiveMinutes();
        if (this.postAdapter.getPostById(id) == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        var result = this.postAdapter.updatePost(id, model);
        return Response.status(HttpResponseStatus.NO_CONTENT.code())
                       .header("Location", createLocationHeader(result))
                       .tag(Long.toString(result.hashCode()))
                       .cacheControl(this.cacheControl)
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
        if (toBeDeleted == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        if (this.postAdapter.deletePost(toBeDeleted)) {
            return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
        } else {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
    }

    @POST
    @Path("populate")
    public Response populateDatabase() {
        setCacheControlFiveMinutes();
        var result = this.postAdapter.createPost(postMapper.postDtoToCreatePostDto(
                postMapper.postToPostDto(postFaker.createModel())));
        return Response.status(HttpResponseStatus.CREATED.code())
                       .header("Location", createLocationHeader(result))
                       .tag(Long.toString(result.hashCode()))
                       .cacheControl(this.cacheControl)
                       .build();
    }


    private String createLocationHeader(PostDto model) {
        return uriInfo.getRequestUriBuilder().path(Long.toString(model.getId())).build().toString();
    }

    private void setCacheControlFiveMinutes() {
        this.cacheControl = new CacheControl();
        this.cacheControl.setMaxAge(300);
    }

}
