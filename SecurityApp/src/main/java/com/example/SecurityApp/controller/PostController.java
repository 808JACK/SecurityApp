package com.example.SecurityApp.controller;

import com.example.SecurityApp.dto.PostDTO;
import com.example.SecurityApp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts(); // Add this method in your service
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO input) {
        return postService.createNewPost(input);
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }
}
