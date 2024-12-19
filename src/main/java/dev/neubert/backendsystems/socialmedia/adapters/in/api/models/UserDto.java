package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.util.List;

public class UserDto extends AbstractDataTransferObject {
    private String username; // This is the CN from the THWS Auth
    private String displayName;

    private List<PostDto> posts;
    private List<LikeDto> likes;

    public UserDto() {}

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

    public List<PostDto> getPosts() {
        return posts;
    }

    public List<LikeDto> getLikes() {
        return likes;
    }
}
