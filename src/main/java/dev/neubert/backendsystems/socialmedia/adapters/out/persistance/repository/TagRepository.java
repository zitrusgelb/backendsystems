package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.CreateTagOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.DeleteTagOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.ReadAllTagsOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.UpdateTagOut;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.jboss.resteasy.util.NoContent;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class TagRepository implements CreateTagOut, UpdateTagOut, ReadAllTagsOut, DeleteTagOut {
    private final TagMapper mapper = Mappers.getMapper(TagMapper.class);

    @Inject
    private EntityManager entityManager;

    @Override
    public Tag createTag(Tag tag) {
        final var entity = this.mapper.tagToTagEntity(tag);
        this.entityManager.persist(entity);
        return tag;
    }

    @Override
    public boolean deleteTag(long id) {
        final var entity = this.entityManager.find(Tag.class, id);
        this.entityManager.remove(entity);
        return true;
    }

    @Override
    public List<Tag> readAllTags(int limit, int offset) {
        List<Tag> returnValue = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
            Root<TagEntity> from = cq.from(TagEntity.class);
            cq.select(from);
            TypedQuery<TagEntity> query = entityManager.createQuery(cq);
            final var requestedModel =
                    query.setFirstResult(offset).setMaxResults(limit).getResultList();
            if (requestedModel != null) {
                for (TagEntity tag : requestedModel) {
                    returnValue.add(mapper.tagEntityToTag(tag));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }
    public Tag findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        }
        Tag tag = tagStorage.get(id);
        if (tag == null) {
            throw new IllegalArgumentException("Tag with ID " + id + " not found");
        }
        return tag;
    }


    @Override
    public List<Tag> readAllTags(int limit) {
        return readAllTags(limit, 0);
    }

    @Override
    public Tag updateTag(long id, Tag tag) {
        final var entity = this.mapper.tagToTagEntity(tag);
        this.entityManager.merge(entity);
        return tag;
    }
}
