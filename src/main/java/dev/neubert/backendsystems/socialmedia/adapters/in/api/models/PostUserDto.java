package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

public class PostUserDto extends AbstractDataTransferObject {
    private String username;
    private String displayName;

    public PostUserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}