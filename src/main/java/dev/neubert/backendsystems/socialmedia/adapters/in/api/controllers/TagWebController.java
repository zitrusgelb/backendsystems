package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.adapter.TagAdapter;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.stream.Collectors;

@Path("tags")
public class TagWebController {

    @Inject
    TagAdapter tagAdapter;

    @Context
    private UriInfo uriInfo;
    private HttpHeaders httpHeaders;
    private CacheControl cacheControl;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response readAllTags(
            @DefaultValue("") @QueryParam("q") String query,
            @PositiveOrZero @DefaultValue("0") @QueryParam("offset") long offset,
            @Positive @DefaultValue("20") @QueryParam("size") long size
    ) {
        setCacheControlFiveMinutes();
        var allTags = this.tagAdapter.readAllTags();
        var filteredTags = allTags.stream().filter(tag -> tag.getName().contains(query)).toList();
        var result = filteredTags.stream().skip(offset).limit(size).collect(Collectors.toList());
        return Response.status(HttpResponseStatus.OK.code())
                       .header("X-Total-Count", result.size())
                       .cacheControl(this.cacheControl)
                       .entity(result)
                       .build();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@Positive @PathParam("id") long id) {
        setCacheControlFiveMinutes();
        var requestedTag = this.tagAdapter.getTagById(id);
        if (requestedTag == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        return Response.ok(requestedTag)
                       .tag(Long.toString(requestedTag.hashCode()))
                       .cacheControl(this.cacheControl)
                       .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createTag(
            @Valid
            CreateTagDto model
    ) {
        setCacheControlFiveMinutes();
        var result = this.tagAdapter.createTag(model);
        return Response.status(HttpResponseStatus.CREATED.code())
                       .header("Location", createLocationHeader(result))
                       .tag(Long.toString(result.hashCode()))
                       .cacheControl(this.cacheControl)
                       .build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateTag(@Positive @PathParam("id") long id, @Valid TagDto model) {
        setCacheControlFiveMinutes();
        if (this.tagAdapter.getTagById(id) == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        var result = this.tagAdapter.updateTag(id, model);
        return Response.status(HttpResponseStatus.NO_CONTENT.code())
                       .header("Location", createLocationHeader(result))
                       .tag(Long.toString(result.hashCode()))
                       .cacheControl(this.cacheControl)
                       .build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTag(@Positive @PathParam("id") long id) {
        var toBeDeleted = this.tagAdapter.getTagById(id);
        if (toBeDeleted == null) {
            return Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
        }
        if (this.tagAdapter.deleteTag(toBeDeleted)) {
            return Response.status(HttpResponseStatus.NO_CONTENT.code()).build();
        } else {
            return Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).build();
        }
    }

    private String createLocationHeader(TagDto model) {
        return uriInfo.getRequestUriBuilder().path(Long.toString(model.getId())).build().toString();
    }

    private void setCacheControlFiveMinutes() {
        this.cacheControl = new CacheControl();
        this.cacheControl.setMaxAge(300);
    }
}
