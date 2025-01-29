package dev.neubert.backendsystems.socialmedia.application.domain.services;

import dev.neubert.backendsystems.socialmedia.adapters.out.persistance.repository.LikeRepository;
import dev.neubert.backendsystems.socialmedia.application.domain.models.Like;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.CreateLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.DeleteLikeIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByPostIn;
import dev.neubert.backendsystems.socialmedia.application.port.in.Like.ReadLikeByUserIn;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class LikeService implements CreateLikeIn, DeleteLikeIn, ReadLikeByPostIn, ReadLikeByUserIn {
    @Inject
    private LikeRepository likeRepository;

    //TODO: user und Post suchen und hinzugfügen

    @Override
    public Like create(Like like) {
        return likeRepository.createLike(like);
    }

    @Override
    public boolean deleteLike(Like like) {
        return likeRepository.deleteLike(like);
    }

    @Override
    public List<Like> readLikeByPost(long postId) {
        return likeRepository.readLikeByPost(postId);
    }

    @Override
    public List<Like> readLikeByUser(long userId) {
        return likeRepository.readLikeByUser(userId);
    }
}
