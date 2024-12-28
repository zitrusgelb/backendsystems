package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.out.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.util.NoContent;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class PostRepository
        implements CreatePostOut, ReadPostOut, UpdatePostOut, DeletePostOut, ReadAllPostsOut {
    private final PostMapper mapper = Mappers.getMapper(PostMapper.class);

    @Inject
    private EntityManager entityManager;

    @Transactional
    @Override
    public NoContent createPost(Post post) {
        final var entity = this.mapper.postToPostEntity(post);
        this.entityManager.persist(entity);
        return new NoContent();
    }

    @Override
    public NoContent deletePost(long postId) {
        this.entityManager.remove(this.entityManager.find(PostEntity.class, postId));
        return new NoContent();
    }

    @Override
    public List<Post> readAllPosts(int limit, int offset) {
        List<Post> returnValue = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PostEntity> cq = cb.createQuery(PostEntity.class);
            Root<PostEntity> from = cq.from(PostEntity.class);
            cq.select(from);
            TypedQuery<PostEntity> query = entityManager.createQuery(cq);
            final var requestedModel =
                    query.setFirstResult(offset).setMaxResults(limit).getResultList();
            if (requestedModel != null) {
                for (PostEntity post : requestedModel) {
                    returnValue.add(mapper.postEntityToPost(post));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }

    @Override
    public List<Post> readAllPosts(int limit) {
        return readAllPosts(limit, 0);
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
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }

    @Transactional
    @Override
    public NoContent updatePost(Post post) {
        final var entity = this.mapper.postToPostEntity(post);
        this.entityManager.merge(entity);
        return new NoContent();
    }
}
