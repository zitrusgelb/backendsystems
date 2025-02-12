package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.User.ReadUserByIdIn;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.CreateUserOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadAllUsersOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserByIdOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserOut;
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
public class UserRepository
        implements CreateUserOut, ReadAllUsersOut, ReadUserOut, ReadUserByIdOut {

    @Inject
    UserMapper mapper;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public User createUser(User user) {
        final var entity = this.mapper.userToUserEntity(user);
        this.entityManager.persist(entity);
        return mapper.userEntityToUser(entity);
    }

    @Override
    public List<User> getAllUsers(int limit, int offset) {
        List<User> returnValue = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
            Root<UserEntity> from = cq.from(UserEntity.class);
            cq.select(from);
            TypedQuery<UserEntity> query = entityManager.createQuery(cq);
            final var requestedModel =
                    query.setFirstResult(offset).setMaxResults(limit).getResultList();
            if (requestedModel != null) {
                for (UserEntity user : requestedModel) {
                    returnValue.add(mapper.userEntityToUser(user));
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }

    @Override
    public List<User> getAllUsers(int limit) {
        return getAllUsers(limit, 0);
    }

    @Override
    public User getUser(String username) {
        User returnValue = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
            Root<UserEntity> from = cq.from(UserEntity.class);
            cq.select(from);
            cq.where(cb.equal(cb.upper(from.get("username")), username.toUpperCase()));
            TypedQuery<UserEntity> query = entityManager.createQuery(cq);
            final var requestedModel = query.getSingleResult();
            if (requestedModel != null) {
                returnValue = mapper.userEntityToUser(requestedModel);
            }
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }

    @Override
    public User getUserById(long id) {
        User returnValue = null;
        try {
            final var requestedModel = this.entityManager.find(UserEntity.class, id);
            if (requestedModel != null) {
                returnValue = mapper.userEntityToUser(requestedModel);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return returnValue;
    }
}
