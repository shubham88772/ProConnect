package com.proconnect.project.postservice.controller;

import com.proconnect.project.postservice.dto.PostCreateRequestDto;
import com.proconnect.project.postservice.dto.PostDto;
import com.proconnect.project.postservice.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto,
                                              HttpServletRequest httpServletRequest){
        PostDto postDto=postService.createPost(postCreateRequestDto, 1L);
        return new ResponseEntity<>(postDto,HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id){
        PostDto postById = postService.getPostById(id);
        return new ResponseEntity<>(postById,HttpStatus.FOUND);
    }

    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>>getAllPostsOfUser(@PathVariable Long userId){
        List<PostDto> allPostsOfUser = postService.getAllPostsOfUser(userId);
        return new ResponseEntity<List<PostDto>>(allPostsOfUser,HttpStatus.OK);
    }

}
