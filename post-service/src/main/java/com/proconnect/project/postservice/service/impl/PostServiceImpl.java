package com.proconnect.project.postservice.service.impl;

import com.proconnect.project.postservice.dto.PostCreateRequestDto;
import com.proconnect.project.postservice.dto.PostDto;
import com.proconnect.project.postservice.entity.Post;
import com.proconnect.project.postservice.exception.ResourceNotFoundException;
import com.proconnect.project.postservice.repository.PostRepository;
import com.proconnect.project.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
        log.info("Creating post of user with id: {}",userId);
        Post toPost=modelMapper.map(postCreateRequestDto,Post.class);
        toPost.setUserId(userId);
        Post save = postRepository.save(toPost);
        return modelMapper.map(save,PostDto.class);
    }

    @Override
//    @Cacheable
    public PostDto getPostById(Long id) {
        log.info("Getting post of user with id: {}", id);
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post not found with id:"+id));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("Getting all the post of user with id: {}" ,userId);
        List<Post> byUserId = postRepository.findByUserId(userId);
        return byUserId
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }
}
