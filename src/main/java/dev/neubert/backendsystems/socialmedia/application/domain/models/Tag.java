package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.util.List;

public class Tag extends AbstractModel {

    private String name;
    private List<Post> posts;

    public Tag(String name, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name; }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}