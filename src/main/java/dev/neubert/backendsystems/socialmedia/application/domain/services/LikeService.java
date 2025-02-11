package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.CreateLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.DeleteLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.CreateLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.DeleteLikeOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.ReadLikeByPostOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Like.ReadLikeByUserOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.Post.ReadPostOut;
import dev.neubert.backendsystems.socialmedia.application.port.out.User.ReadUserByIdOut;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class LikeService implements CreateLikeIn, DeleteLikeIn, ReadLikeByPostIn, ReadLikeByUserIn {

    @Inject
    CreateLikeOut createLikeOut;

    @Inject
    DeleteLikeOut deleteLikeOut;

    @Inject
    ReadLikeByPostOut readLikeByPostOut;

    @Inject
    ReadLikeByUserOut readLikeByUserOut;

    @Inject
    ReadPostOut readPostOut;

    @Inject
    ReadUserByIdOut readUserByIdOut;

    @Override
    public Like create(Like like) {
        var post = readPostOut.getPostById(like.getPost().getId());
        var user = readUserByIdOut.getUserById(like.getUser().getId());
        like.setPost(post);
        like.setUser(user);
        return createLikeOut.createLike(like);
    }

    @Override
    public boolean deleteLike(Like like) {
        return deleteLikeOut.deleteLike(like);
    }

    @Override
    public List<Like> readLikeByPost(long postId) {
        return readLikeByPostOut.readLikeByPost(postId);
    }

    @Override
    public List<Like> readLikeByUser(long userId) {
        return readLikeByUserOut.readLikeByUser(userId);
    }
}
