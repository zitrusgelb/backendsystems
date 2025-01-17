package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.LikeRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Post;
import dev.neubert.backendsystems.socialmedia.application.domain.models.User;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.CreateLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.DeleteLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import jakarta.inject.Inject;

import java.util.List;

public class LikeService implements CreateLikeIn, DeleteLikeIn, ReadLikeByPostIn, ReadLikeByUserIn {
    @Inject
    private LikeRepository likeRepository;

    @Override
    public Like create(Like like) {
        return likeRepository.createLike(like);
    }

    @Override
    public boolean deleteLikeIn(Like like) {
        return likeRepository.deleteLike(like.getId());
    }

    @Override
    public List<Like> readLikeByPost(Post post) {
        return likeRepository.readLikeByPost(post);
    }

    @Override
    public List<Like> readLikeByUser(User user) {
        return likeRepository.readLikeByUser(user);
    }
}
