package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.PostEntity;
import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models.TagEntity;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.PostMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.mapper.TagMapper;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import dev.neubert.backendsystems.socialmedia.application.port.out.CreateTagOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.DeleteTagOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.ReadAllTagsOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.UpdateTagOut;
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
    public NoContent createTag(Tag tag) {
        final var entity = this.mapper.tagToTagEntity(tag);
        this.entityManager.persist(entity);
        return new NoContent();
    }

    @Override
    public NoContent deleteTag(long id) {
        final var entity = this.entityManager.find(Tag.class, id);
        this.entityManager.remove(entity);
        return new NoContent();
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

    @Override
    public List<Tag> readAllTags(int limit) {
        return readAllTags(limit, 0);
    }

    @Override
    public NoContent updateTag(Tag tag) {
        final var entity = this.mapper.tagToTagEntity(tag);
        this.entityManager.merge(entity);
        return new NoContent();
    }
}
