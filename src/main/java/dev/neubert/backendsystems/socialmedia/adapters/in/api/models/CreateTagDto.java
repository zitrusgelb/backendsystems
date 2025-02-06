package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;


import java.io.Serializable;

public class CreateTagDto implements DataTransferObject, Serializable, Cloneable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

