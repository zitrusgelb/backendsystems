package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.port.out.Tag.ReadTagByNameOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TagFaker extends AbstractFaker implements FakerMethods<String> {

    @Inject
    ReadTagByNameOut readTagByNameOut;

    @Override
    public String createModel() {
        String name = faker.esports().team();
        while (readTagByNameOut.getTagByName(name) != null) {
            name = name + faker.esports().team();
        }
        return name;
    }
}
