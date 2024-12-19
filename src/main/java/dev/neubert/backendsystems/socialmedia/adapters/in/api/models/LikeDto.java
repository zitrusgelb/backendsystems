package dev.neubert.backendsystems.socialmedia.adapters.in.api.models;

import java.time.LocalDateTime;

public class LikeDto extends AbstractDataTransferObject {
    private PostDto post;
    private UserDto user;
    private LocalDateTime timestamp;

    public LikeDto(PostDto post, UserDto user, LocalDateTime timestamp) {
        this.post = post;
        this.user = user;
        this.timestamp = timestamp;
    }

    public PostDto getPost() {
        return post;
    }

    public void setPost(PostDto post) {
        this.post = post;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
