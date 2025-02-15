package dev.neubert.backendsystems.socialmedia.adapters.out.persistence.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username; // This is the CN from the THWS Auth
    private String displayName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<PostEntity> posts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<LikeEntity> likes;

    public UserEntity() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public List<LikeEntity> getLikes() {
        return likes;
    }
}
