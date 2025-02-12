package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TagRepository implements CreateTagOut, UpdateTagOut, ReadAllTagsOut, DeleteTagOut,
        ReadTagOut {

    @Inject
    TagMapper mapper;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Tag createTag(Tag tag) {
        final var entity = mapper.tagToTagEntity(tag);
        this.entityManager.persist(entity);
        return tag;
    }

    @Override
    public boolean deleteTag(long id) {
        final var entity = entityManager.find(Tag.class, id);
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
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }

    @Override
    public List<Tag> readAllTags(int limit) {
        return readAllTags(limit, 0);
    }

    public Tag findById(long id) {
        TagEntity entity = entityManager.find(TagEntity.class, id);
        if (entity != null) {
            return mapper.tagEntityToTag(entity);
        }
        return null;
    }

    @Override
    public Tag updateTag(Tag tag) {
        final var entity = mapper.tagToTagEntity(tag);
        entityManager.merge(entity);
        return tag;
    }
    @Override
    public Tag getTagById(long id) {
        TagEntity entity = entityManager.find(TagEntity.class, id);
        if (entity != null) {
            return mapper.tagEntityToTag(entity);
        }
        return null;
    }
}
