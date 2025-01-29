package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models;

import java.io.Serializable;
import java.util.Objects;

public class LikeEntityId implements Serializable {
    private PostEntity post;
    private UserEntity user;

    public LikeEntityId(PostEntity post, UserEntity user) {
        this.post = post;
        this.user = user;
    }

    public LikeEntityId() {}

    public PostEntity getPost() {
        return post;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeEntityId that = (LikeEntityId) o;
        return Objects.equals(post.getId(), that.post.getId()) &&
               Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(post.getId(), user.getId());
    }
}
