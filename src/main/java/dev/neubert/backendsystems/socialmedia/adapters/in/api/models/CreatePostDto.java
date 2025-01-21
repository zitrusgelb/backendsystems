package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.neubert.backendsystems.socialmedia.config.JacksonConfig;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CreatePostDto implements DataTransferObject, Serializable, Cloneable {
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

    @Override
    public String toString() {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper objectMapper = config.objectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}



