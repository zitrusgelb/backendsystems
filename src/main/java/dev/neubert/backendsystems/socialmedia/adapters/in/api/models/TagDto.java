package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.util.List;

public class TagDto {
    private int id;
    private String name;
    private List<PostDto> posts;

    public TagDto(String name, int id, List<PostDto> posts) {
        this.name = name;
        this.id = id;
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}