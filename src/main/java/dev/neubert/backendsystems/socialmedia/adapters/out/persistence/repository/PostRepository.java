package dev.neubert.backendsystems.socialmedia.adapters.out.persistence.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.port.out.Post.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PostRepository
        implements CreatePostOut, ReadPostOut, UpdatePostOut, DeletePostOut, ReadAllPostsOut {

    @Inject
    PostMapper mapper;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Post createPost(Post post) {
        final var entity = this.mapper.postToPostEntity(post);
        entity.setTag(entityManager.find(TagEntity.class,
                                         post.getTag().getId())); // assures Manged Entity
        this.entityManager.persist(entity);
        return mapper.postEntityToPost(entityManager.find(PostEntity.class, entity.getId()));
    }

    @Transactional
    @Override
    public boolean deletePost(long postId) {
        try {
            this.entityManager.remove(this.entityManager.find(PostEntity.class, postId));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<Post> readAllPosts(String queryString, int offset, int limit) {
        List<Post> returnValue = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostEntity> cq = cb.createQuery(PostEntity.class);
            Root<PostEntity> from = cq.from(PostEntity.class);
            cq.select(from);
            if (queryString != null && !queryString.isEmpty()) {
                cq.where(cb.like(cb.upper(from.get("content")),
                                 "%" + queryString.toUpperCase() + "%"));
            }
            TypedQuery<PostEntity> query = entityManager.createQuery(cq);
            final var requestedModel =
                    query.setFirstResult(offset).setMaxResults(limit).getResultList();
            if (requestedModel != null) {
                for (PostEntity post : requestedModel) {
                    returnValue.add(mapper.postEntityToPost(post));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }

    @Override
    public Post getPostById(long id) {
        Post returnValue = null;
        try {
            final var requestedModel = this.entityManager.find(PostEntity.class, id);
            if (requestedModel != null) {
                returnValue = mapper.postEntityToPost(requestedModel);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }

    @Transactional
    @Override
    public Post updatePost(Post post) {
        final var entity = this.mapper.postToPostEntity(post);
        this.entityManager.merge(entity);
        return post;
    }
}
