package dev.neubert.backendsystems.socialmedia.application.domain.models;

import java.time.LocalDateTime;

public class Like extends AbstractModel
{
    private Post post;
    private User user;
    private LocalDateTime timestamp;

    public Like(Post post)
    {
        this.post = post;
        this.user = user;
        timestamp = LocalDateTime.now();
    }

    public Post getPost()
    {
        return post;
    }

    public void setPost(Post post)
    {
        this.post = post;
    }
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user=user;
    }


    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }
}
