package com.proconnect.project.postservice.service;

public interface PostLikeService{


    void likePost(Long postId);

    void unlikePost(Long postId);
}
