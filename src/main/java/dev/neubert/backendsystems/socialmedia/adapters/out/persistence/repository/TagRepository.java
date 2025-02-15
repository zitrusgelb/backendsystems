package dev.neubert.backendsystems.socialmedia.adapters.out.persistence.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.TagEntity;
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
        Tag existingTag = getTagByName(name);
        if (getTagByName(name) != null) {
            return existingTag;
        }
        var entity = new TagEntity();
        entity.setName(name);
        entityManager.persist(entity);
        return mapper.tagEntityToTag(entity);
    }

    @Transactional
    @Override
    public boolean deleteTag(String name) {
        try {
            long tagId = getTagByName(name).getId();
            var posts = getPostsWithTag(tagId);
            for (var post : posts) {
                post.setTag(null);
                entityManager.merge(post);
            }
            this.entityManager.remove(entityManager.find(TagEntity.class, tagId));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<Tag> readAllTags(String query, int offset, int limit) {
        List<Tag> returnValue = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
            Root<TagEntity> from = cq.from(TagEntity.class);
            if (query != null && !query.trim().isEmpty()) {
                cq.where(cb.like(cb.lower(from.get("name")), "%" + query.toLowerCase() + "%"));
            }
            cq.select(from);
            TypedQuery<TagEntity> typedQuery =
                    entityManager.createQuery(cq).setFirstResult(offset).setMaxResults(limit);
            List<TagEntity> requestedModel = typedQuery.getResultList();

            if (requestedModel != null) {
                for (TagEntity tag : requestedModel) {
                    returnValue.add(mapper.tagEntityToTag(tag));
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Tags: " + e.getMessage());
            return null;
        }

        return returnValue;
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
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
            Root<TagEntity> from = cq.from(TagEntity.class);
            cq.select(from);
            cq.where(cb.equal(cb.upper(from.get("name")), name.toUpperCase()));
            TypedQuery<TagEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getSingleResult();
            return mapper.tagEntityToTag(requestedModel);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private List<PostEntity> getPostsWithTag(long tagId) {
        try {
            var tagEntity = this.entityManager.find(TagEntity.class, tagId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostEntity> cq = cb.createQuery(PostEntity.class);
            Root<PostEntity> from = cq.from(PostEntity.class);
            cq.select(from);
            cq.where(cb.equal(from.get("tag"), tagEntity));
            TypedQuery<PostEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getResultList();
            return requestedModel == null ? new ArrayList<>() : requestedModel;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return List.of();
        }
    }
}
