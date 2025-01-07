package dev.neubert.backendsystems.socialmedia.application.domain.fakers;

import dev.neubert.backendsystems.socialmedia.application.domain.models.AbstractModel;

public interface FakerMethods<T> {
    T createModel();
}
