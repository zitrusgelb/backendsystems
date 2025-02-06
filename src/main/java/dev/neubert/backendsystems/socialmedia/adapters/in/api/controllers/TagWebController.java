package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.CreateTagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Path("tags")
public class TagWebController {

    @Inject
    CreateTagIn createTagIn;

    @Inject
    DeleteTagIn deleteTagIn;

    @Inject
    ReadAllTagsIn readAllTagsIn;

    @Inject
    ReadTagIn readTagIn;

    @Inject
    UpdateTagIn updateTagIn;

    @inject

    TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Context
    private UriInfo uriInfo;
    private HttpHeaders httpHeaders;
    private CacheControl cacheControl;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response readAllTags(
            @DefaultValue("") @QueryParam("q") String query,
            @PositiveOrZero @DefaultValue("0") @QueryParam("offset") int offset,
            @Positive @DefaultValue("10") @QueryParam("limit") int limit) {
        List<Tag> tags = readAllTagsIn.readAllTags();
        List<TagDto> tagDtos = tags.stream().map(tagMapper::tagToTagDto).collect(Collectors.toList());
        return Response.ok(tagDtos).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createTag(@Valid CreateTagDto tagDto) {
        TagDto createdTag = tagMapper.tagToTagDto(createTagIn.createTag(tagMapper.createTagDtoToTag(tagDto)));
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(createdTag.getId()));
        return Response.created(builder.build()).entity(createdTag).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTagById(@PathParam("id") long id) {
        Tag tag = readTagIn.getTagById(id);
        return Response.ok(tagMapper.tagToTagDto(tag)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateTag(@PathParam("id") long id, @Valid TagDto tagDto) {
        TagDto updatedTag = tagMapper.tagToTagDto(updateTagIn.update(id, tagMapper.tagDtoToTag(tagDto)));
        return Response.ok(updatedTag).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteTag(@PathParam("id") long id) {
        Tag tag = readTagIn.getTagById(id);
        boolean deleted = deleteTagIn.deleteTag(tag);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
