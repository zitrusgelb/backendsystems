package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity user;

    @ManyToOne
    private TagEntity tag;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity replyTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public PostEntity getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(PostEntity replyTo) {
        this.replyTo = replyTo;
    }
}
