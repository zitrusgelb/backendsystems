package dev.neubert.backendsystems.socialmedia.application.domain.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neubert.backendsystems.socialmedia.config.JacksonConfig;

public abstract class AbstractModel implements Model {
    protected long id;

    public AbstractModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper objectMapper = config.objectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
