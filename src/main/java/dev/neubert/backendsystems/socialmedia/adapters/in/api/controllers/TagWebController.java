package dev.neubert.backendsystems.socialmedia.adapters.in.api.controllers;

import dev.neubert.backendsystems.socialmedia.adapters.in.api.models.TagDto;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.AuthorizationBinding;
import dev.neubert.backendsystems.socialmedia.adapters.in.api.utils.Cached;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.in.Tag.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

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

    @Inject
    TagMapper tagMapper;

    @Context
    private UriInfo uriInfo;
    private HttpHeaders httpHeaders;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Cached
    public Response readAllTags(
            @DefaultValue("")
            @QueryParam("q")
            String query,
            @PositiveOrZero
            @DefaultValue("0")
            @QueryParam("offset")
            int offset,
            @Positive
            @DefaultValue("10")
            @QueryParam("limit")
            int limit
    ) {
        try {
            List<Tag> tags = readAllTagsIn.readAllTags();
            if (tags.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            List<TagDto> tagDtos =
                    tags.stream().map(tagMapper::tagToTagDto).collect(Collectors.toList());
            return Response.ok(tagDtos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Fehler beim Laden der Tags")
                           .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Cached
    public Response getTagById(
            @PathParam("id")
            long id
    ) {
        try {
            Tag tag = readTagIn.getTagById(id);
            if (tag == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Tag nicht gefunden")
                               .build();
            }
            return Response.ok(tagMapper.tagToTagDto(tag)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Fehler beim Abrufen des Tags")
                           .build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @AuthorizationBinding
    @Cached
    public Response updateTag(
            @PathParam("id")
            long id,
            @Valid
            TagDto tagDto
    ) {
        try {
            if (tagDto == null || tagDto.getName() == null || tagDto.getName().isBlank()) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Tag-Name darf nicht leer sein")
                               .build();
            }
            Tag existingTag = readTagIn.getTagById(id);
            if (existingTag == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Tag nicht gefunden")
                               .build();
            }
            TagDto updatedTag =
                    tagMapper.tagToTagDto(updateTagIn.update(id, tagMapper.tagDtoToTag(tagDto)));
            return Response.ok(updatedTag).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Fehler beim Aktualisieren des Tags")
                           .build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @AuthorizationBinding
    public Response deleteTag(
            @PathParam("id")
            long id
    ) {
        try {
            Tag tag = readTagIn.getTagById(id);
            if (tag == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Tag nicht gefunden")
                               .build();
            }
            boolean deleted = deleteTagIn.deleteTag(tag.getName());
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                               .entity("Fehler beim Löschen des Tags")
                               .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Fehler beim Löschen des Tags")
                           .build();
        }
    }
}
