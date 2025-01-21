package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neubert.backendsystems.socialmedia.config.JacksonConfig;

import java.io.Serializable;

public abstract class AbstractDataTransferObject implements DataTransferObject,Serializable, Cloneable {
    protected long id;

    public AbstractDataTransferObject() {
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
