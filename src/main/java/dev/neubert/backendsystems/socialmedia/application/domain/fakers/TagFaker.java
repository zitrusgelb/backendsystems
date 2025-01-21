package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagFaker extends AbstractFaker implements FakerMethods<Tag> {
    @Override
    public Tag createModel() {
        String name = faker.esports().team();
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
