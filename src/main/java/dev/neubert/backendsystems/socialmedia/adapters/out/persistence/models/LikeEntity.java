package dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(LikeEntityId.class)
public class LikeEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private PostEntity post;

    @ManyToOne(cascade = CascadeType.ALL)
    @Id
    private UserEntity user;

    private LocalDateTime timestamp = LocalDateTime.now();

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
