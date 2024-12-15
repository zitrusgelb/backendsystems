package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

public class UserFaker extends AbstractFaker implements FakerMethods {
    @Override
    public Object createModel() {
        return null;
    }


    /*@Override
    public User createModel() {
        String username = faker.pokemon().name();
        String displayName = faker.artist().name();

        return new User(username, displayName);
    }*/
}
