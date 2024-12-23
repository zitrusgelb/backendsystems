package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.LikeEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.LikeMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.out.CreateLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.DeleteLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.ReadLikeByPostOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.ReadLikeByUserOut;
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

public class LikeRepository
        implements CreateLikeOut, ReadLikeByPostOut, ReadLikeByUserOut, DeleteLikeOut {
    private final LikeMapper mapper = Mappers.getMapper(LikeMapper.class);

    @Inject
    private EntityManager entityManager;


    @Override
    public NoContent createLike(Like like) {
        final var entity = this.mapper.likeToLikeEntity(like);
        this.entityManager.persist(entity);
        return new NoContent();
    }

    @Override
    public NoContent deleteLike(long id) {
        final var entity = this.entityManager.find(Like.class, id);
        this.entityManager.remove(entity);
        return new NoContent();
    }

    @Override
    public List<Like> readLikeByPost(Post post) {
        List<Like> returnValue = new ArrayList<>();

        try {
            var postEntity = this.entityManager.find(PostEntity.class, post.getId());
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<LikeEntity> cq = cb.createQuery(LikeEntity.class);
            Root<LikeEntity> from = cq.from(LikeEntity.class);
            cq.select(from);
            cq.where(cb.equal(from.get("post"), postEntity));
            TypedQuery<LikeEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getResultList();
            if (requestedModel != null) {
                for (LikeEntity like : requestedModel) {
                    returnValue.add(mapper.likeEntityToLike(like));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }

    @Override
    public List<Like> readLikeByUser(User user) {
        List<Like> returnValue = new ArrayList<>();

        try {
            var userEntity = this.entityManager.find(UserEntity.class, user.getId());
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<LikeEntity> cq = cb.createQuery(LikeEntity.class);
            Root<LikeEntity> from = cq.from(LikeEntity.class);
            cq.select(from);
            cq.where(cb.equal(from.get("user"), userEntity));
            TypedQuery<LikeEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getResultList();
            if (requestedModel != null) {
                for (LikeEntity like : requestedModel) {
                    returnValue.add(mapper.likeEntityToLike(like));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }
}
