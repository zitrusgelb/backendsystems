package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.time.LocalDateTime;

public class PostDto extends AbstractDataTransferObject {
    private String content;
    private LocalDateTime createdAt;
    private UserDto user;
    private TagDto tag;
    private PostDto replyTo;
    private int version;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public TagDto getTag() {
        return tag;
    }

    public void setTag(TagDto tag) {
        this.tag = tag;
    }

    public PostDto getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(PostDto replyTo) {
        this.replyTo = replyTo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}



