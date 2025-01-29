package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.time.LocalDateTime;

public class Like extends AbstractModel {
    private Post post;
    private User user;
    private LocalDateTime timestamp;

    public Like() {
    }

    public Like(Post post, User user, LocalDateTime timestamp) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        if (like.getPost().getId() != post.getId()) return false;
        if (like.getUser().getId() != user.getId()) return false;
        if (like.getTimestamp() != null
                ? !like.getTimestamp().equals(timestamp)
                : timestamp != null) {
            return false;
        }
        return true;
    }
}
