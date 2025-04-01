package com.proconnect.project.postservice.service;

import com.proconnect.project.postservice.dto.PostCreateRequestDto;
import com.proconnect.project.postservice.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId);

    PostDto getPostById(Long id);

    List<PostDto> getAllPostsOfUser(Long userId);
}
