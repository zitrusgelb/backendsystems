package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.time.LocalDateTime;

public class PostDto extends AbstractDataTransferObject {
    private String content;
    private LocalDateTime createdAt;
    private long userId;
    private long tagId;
    private long replyToId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(long replyToId) {
        this.replyToId = replyToId;
    }

}



