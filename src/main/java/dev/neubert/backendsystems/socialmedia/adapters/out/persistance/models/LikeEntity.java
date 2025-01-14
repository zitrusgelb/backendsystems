package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(LikeEntityId.class)
public class LikeEntity
{
    @OneToOne
    @Id
    private PostEntity post;

    @OneToOne
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
