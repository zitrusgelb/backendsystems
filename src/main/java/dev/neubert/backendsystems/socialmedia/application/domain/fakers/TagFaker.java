package dev.neubert.backendsystems.socialmedia.application.domain.fakers;


import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

import java.util.Random;

public class TagFaker extends AbstractFaker implements FakerMethods<Tag> {
    @Override
    public Tag createModel() {
        String name = faker.esports().team();
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
