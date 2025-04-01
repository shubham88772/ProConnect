package com.proconnect.project.postservice.service.impl;

import com.proconnect.project.postservice.entity.Post;
import com.proconnect.project.postservice.entity.PostLike;
import com.proconnect.project.postservice.exception.BadRequestException;
import com.proconnect.project.postservice.exception.ResourceNotFoundException;
import com.proconnect.project.postservice.repository.PostLikeRepository;
import com.proconnect.project.postservice.repository.PostRepository;
import com.proconnect.project.postservice.service.PostLikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void likePost(Long postId) {
        Long userId= 1L;
        log.info("User with Id: {} liking the post with Id: {}",userId,postId);
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id:"+postId));
        boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(hasAlreadyLiked) throw new BadRequestException("You cannot like the post again with id"+postId);
        PostLike like=new PostLike();
        like.setPostId(postId);
        like.setUserId(userId);
//        like.setCreatedAt(LocalDateTime.now());
        postLikeRepository.save(like);
        //TODO: Send notification to the owner of the post.
    }

    @Override
    @Transactional
    public void unlikePost(Long postId) {
        Long userId=1L;
        log.info("User with Id: {} unliking the post with Id: {}",userId,postId);
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id:"+postId));
        boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if (!hasAlreadyLiked) throw new BadRequestException("You cannot unlike the post as you haven't liked with id"+postId);
        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

    }
}
