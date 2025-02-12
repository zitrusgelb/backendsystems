package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TagRepository
        implements CreateTagOut, UpdateTagOut, ReadAllTagsOut, DeleteTagOut, ReadTagOut,
        ReadTagByNameOut {

    @Inject
    TagMapper mapper;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Tag createTag(String name) {
        if (getTagByName(name) != null) {
            return getTagByName(name);
        }
        final var entity = new TagEntity();
        entity.setName(name);
        this.entityManager.persist(entity);
        return mapper.tagEntityToTag(entityManager.find(TagEntity.class, entity.getId()));
    }

    @Transactional
    @Override
    public boolean deleteTag(long id) {
        try {
            this.entityManager.remove(entityManager.find(TagEntity.class, id));
            return true;
        } catch (Exception e) {
            return false;
        }

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

    @Transactional
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

    @Override
    public Tag getTagByName(String name) {
        Tag returnValue = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
            Root<TagEntity> from = cq.from(TagEntity.class);
            cq.select(from);
            cq.where(cb.equal(cb.upper(from.get("name")), name.toUpperCase()));
            TypedQuery<TagEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getSingleResult();
            if (requestedModel != null) {
                returnValue = mapper.tagEntityToTag(requestedModel);
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return returnValue;
    }
}
