package dev.neubert.backendsystems.socialmedia.application.domain.models;

public abstract class AbstractModel {
    private long id;

    public AbstractModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
