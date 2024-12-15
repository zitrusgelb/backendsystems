package dev.neubert.backendsystems.socialmedia.adapters.out.persistance.models;

import java.io.Serializable;

public class LikeEntityId implements Serializable
{
    private PostEntity post;
   // private UserEntity user;

    public LikeEntityId(PostEntity post){
        this.post = post;
        //this.user = user;
    }
}
