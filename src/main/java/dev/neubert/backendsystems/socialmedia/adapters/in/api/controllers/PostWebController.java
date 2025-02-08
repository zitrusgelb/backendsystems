package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreatePostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.PostDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthorizationBinding;
import dev.neubert.backendsystems.socialmedia.application.domain.fakers.PostFaker;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.port.in.Post.*;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Path("posts")
public class PostWebController {

    @Inject
    CreatePostIn createPostIn;

    @Inject
    DeletePostIn deletePostIn;

    @Inject
    ReadAllPostsIn readAllPostsIn;

    @Inject
    ReadPostIn readPostIn;

    @Inject
    UpdatePostIn updatePostIn;

    @Inject
    PostFaker postFaker;

    @Inject
    PostMapper postMapper;

    @Inject
    ReadUserByIdIn readUserByIdIn;

    @Context
    private UriInfo uriInfo;

    @Context
    private Request request;

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
        var allPosts = this.readAllPostsIn.readAllPosts();
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
            @HeaderParam("If-None-Match")
            String ifNoneMatch,
            @Positive
            @PathParam("id")
            long id
    ) {
        var requestedPost = this.readPostIn.getPostById(id);
        if (requestedPost == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        if (ifNoneMatch != null && ifNoneMatch.equals("v" + requestedPost.getVersion())) {
            return Response.status(HttpResponseStatus.NOT_MODIFIED.code())
                           .tag(new EntityTag("v" + requestedPost.getVersion()))
                           .build();
        } else {
            setCacheControlFiveMinutes();
            return Response.ok(requestedPost)
                           .cacheControl(this.cacheControl)
                           .tag(new EntityTag("v" + requestedPost.getVersion()))
                           .build();
        }
    }


    @AuthorizationBinding
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPost(
            @HeaderParam("X-User-Id")
            String userId,
            @Valid
            CreatePostDto model
    ) {
        setCacheControlFiveMinutes();
        if (model == null) {
            return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
        }
        model.setUsername(getUsernameFromHeader(userId));
        model.setCreatedAt(LocalDateTime.now());
        var result = this.createPostIn.create(postMapper.createPostDtoToPost(model));
        return Response.status(HttpResponseStatus.CREATED.code())
                       .header("Location", createLocationHeader(postMapper.postToPostDto(result)))
                       .tag(new EntityTag("v" + result.getVersion()))
                       .cacheControl(this.cacheControl)
                       .build();
    }

    @AuthorizationBinding
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updatePost(
            @HeaderParam("X-User-Id")
            String userId,
            @HeaderParam("If-Match")
            String ifMatch,
            @Positive
            @PathParam("id")
            long id,
            @Valid
            PostDto model
    ) {
        Response.ResponseBuilder conditionalResponse = null;
        EntityTag requestTag = null;
        if (ifMatch != null) {
            requestTag = new EntityTag(ifMatch);
            conditionalResponse = request.evaluatePreconditions(requestTag);
        }
        if (conditionalResponse != null) {
            return conditionalResponse.build();
        } else {
            if (model == null) {
                return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
            } else if (model.getUser() == null) {
                return Response.status(HttpResponseStatus.BAD_REQUEST.code()).build();
            }
            setCacheControlFiveMinutes();
            var toBeUpdated = readPostIn.getPostById(id);
            if (toBeUpdated == null) {
                return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
            } else if (!getUsernameFromHeader(userId).equals(toBeUpdated.getUser().getUsername())) {
                return Response.status(HttpResponseStatus.UNAUTHORIZED.code()).build();
            }

            EntityTag currentTag = new EntityTag("v" + toBeUpdated.getVersion());
            if (requestTag == null || !requestTag.equals(currentTag)) {
                return Response.status(HttpResponseStatus.PRECONDITION_FAILED.code()).build();
            }

            var result = this.updatePostIn.updatePost(id, postMapper.postDtoToPost(model));
            return Response.status(HttpResponseStatus.NO_CONTENT.code())
                           .header("Location",
                                   createLocationHeader(postMapper.postToPostDto(result)))
                           .tag(new EntityTag("v" + result.getVersion()))
                           .cacheControl(this.cacheControl)
                           .build();
        }
    }

    @AuthorizationBinding
    @DELETE
    @Path("{id}")
    public Response deletePost(
            @HeaderParam("X-User-Id")
            String userId,
            @Positive
            @PathParam("id")
            long id
    ) {
        var toBeDeleted = this.readPostIn.getPostById(id);
        if (toBeDeleted == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        } else if (!getUsernameFromHeader(userId).equals(toBeDeleted.getUser().getUsername())) {
            return Response.status(HttpResponseStatus.UNAUTHORIZED.code()).build();
        } else if (this.deletePostIn.delete(toBeDeleted)) {
            return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
        } else {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
    }

    @POST
    @Path("populate")
    public Response populateDatabase() {
        setCacheControlFiveMinutes();
        var result = postFaker.createModel();
        createPostIn.create(result);
        return Response.status(HttpResponseStatus.CREATED.code()).build();
    }

    private String createLocationHeader(PostDto model) {
        return uriInfo.getRequestUriBuilder().path(Long.toString(model.getId())).build().toString();
    }

    private void setCacheControlFiveMinutes() {
        this.cacheControl = new CacheControl();
        this.cacheControl.setMaxAge(300);
    }

    private String getUsernameFromHeader(String userId) {
        var user = readUserByIdIn.getUserById(Long.parseLong(userId));
        return user.getUsername();
    }
}
