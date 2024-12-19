package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import com.github.javafaker.Faker;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;

public abstract class AbstractFaker {
    protected final Faker faker = new Faker();
}
