package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.UserEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.UserMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.out.CreateUserOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.ReadAllUsersOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.ReadUserOut;
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

public class UserRepository implements CreateUserOut, ReadAllUsersOut, ReadUserOut {
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Inject
    private EntityManager entityManager;

    @Transactional
    @Override
    public NoContent createUser(User user) {
        final var entity = this.mapper.userToUserEntity(user);
        this.entityManager.persist(entity);
        return new NoContent();
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
            e.printStackTrace();
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
            final var requestedModel = this.entityManager.find(UserEntity.class, username);
            if (requestedModel != null) {
                returnValue = mapper.userEntityToUser(requestedModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return returnValue;
    }
}
