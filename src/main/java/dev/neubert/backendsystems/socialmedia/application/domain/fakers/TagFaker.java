package dev.neubert.backendsystems.socialmedia.application.domain.fakers;


import dev.neubert.backendsystems.socialmedia.application.domain.models.Tag;

import java.util.Random;

public class TagFaker extends AbstractFaker implements FakerMethods<Tag> {
    @Override
    public Tag createModel() {
        String name = faker.esports().team();
        int id = new Random().nextInt(0, 99999999);
        return new Tag(name, id, null);
    }
}
