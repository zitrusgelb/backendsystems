package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.util.ArrayList;
import java.util.List;

public class User extends AbstractModel {

    private String username; // This is the CN from the THWS Auth
    private String displayName;

    private final List<Post> posts = new ArrayList<>();
    private final List<Like> likes = new ArrayList<>();

    public User() {}

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
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

    public List<Post> getPosts() {
        return posts;
    }

    public List<Like> getLikes() {
        return likes;
    }
}
