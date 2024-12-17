package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.util.List;

public class Tag {

    private final String name;
    private final int id;
    private List<Post> posts;

    public Tag(String name, int id, List<Post> posts) {
        this.name = name;
        this.id = id;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}