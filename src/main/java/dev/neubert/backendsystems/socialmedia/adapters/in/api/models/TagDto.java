package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.util.List;

public class TagDto extends AbstractDataTransferObject {
    private String name;
    private List<PostDto> posts;

    public TagDto() {}

    public TagDto(String name, List<PostDto> posts) {
        this.name = name;
        this.posts = posts;
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