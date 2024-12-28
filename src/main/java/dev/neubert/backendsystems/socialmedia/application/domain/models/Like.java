package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.time.LocalDateTime;

public class Like extends AbstractModel {
    private Post post;
    private User user;
    private LocalDateTime timestamp;

    public Like() {}

    public Like(Post post, User user, LocalDateTime timestamp)
    {
        this.post = post;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
