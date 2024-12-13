package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.io.Serializable;

public abstract class AbstractDataTransferObject implements Serializable, Cloneable {
    private long id;

    public AbstractDataTransferObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
