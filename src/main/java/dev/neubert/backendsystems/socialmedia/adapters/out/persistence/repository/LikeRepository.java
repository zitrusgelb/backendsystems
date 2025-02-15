package dev.neubert.backendsystems.socialmedia.adapters.out.persistence.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.LikeEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.LikeEntityId;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.CreateLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.DeleteLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.ReadLikeByPostOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.ReadLikeByUserOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LikeRepository
        implements CreateLikeOut, ReadLikeByPostOut, ReadLikeByUserOut, DeleteLikeOut {

    @Inject
    LikeMapper mapper;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Like createLike(Like like) {
        try {
            final var entity = this.mapper.likeToLikeEntity(like);
            this.entityManager.merge(entity);

            PostEntity postEntity = entityManager.find(PostEntity.class, like.getPost().getId());
            UserEntity userEntity = entityManager.find(UserEntity.class, like.getUser().getId());
            LikeEntityId likeEntityId = new LikeEntityId(postEntity, userEntity);
            LikeEntity persistedEntity = this.entityManager.find(LikeEntity.class, likeEntityId);

            return mapper.likeEntityToLike(persistedEntity);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteLike(Like like) {
        try {
            PostEntity postEntity =
                    entityManager.getReference(PostEntity.class, like.getPost().getId());
            UserEntity userEntity =
                    entityManager.getReference(UserEntity.class, like.getUser().getId());
            LikeEntityId likeEntityId = new LikeEntityId(postEntity, userEntity);

            LikeEntity entity = entityManager.find(LikeEntity.class, likeEntityId);
            if (entity != null) {
                entityManager.remove(entity);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Like> readLikeByPost(long postId) {
        try {
            var postEntity = this.entityManager.find(PostEntity.class, postId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<LikeEntity> cq = cb.createQuery(LikeEntity.class);
            Root<LikeEntity> from = cq.from(LikeEntity.class);
            cq.select(from);
            cq.where(cb.equal(from.get("post"), postEntity));
            TypedQuery<LikeEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getResultList();

            return requestedModel.stream()
                                 .map(mapper::likeEntityToLike)
                                 .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Like> readLikeByUser(long userId) {
        try {
            var userEntity = this.entityManager.find(UserEntity.class, userId);
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<LikeEntity> cq = cb.createQuery(LikeEntity.class);
            Root<LikeEntity> from = cq.from(LikeEntity.class);
            cq.select(from);
            cq.where(cb.equal(from.get("user"), userEntity));
            TypedQuery<LikeEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getResultList();

            return requestedModel.stream()
                                 .map(mapper::likeEntityToLike)
                                 .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return List.of();
        }
    }
}
