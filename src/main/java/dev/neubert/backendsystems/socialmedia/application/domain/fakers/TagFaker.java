package dev.neubert.backendsystems.socialmedia.application.domain.fakers;


import java.util.Random;

public class TagFaker extends AbstractFaker implements FakerMethods {
    @Override
    public Object createModel() {
        return null;
    }
/*
    @Override
    public Tag createModel() {
        String name = faker.esports().team();
        int id = new Random().nextInt(0, 99999999);
        return new Tag(name, id, null);
    }*/
}
