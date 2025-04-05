package com.example.SecurityApp.controller;

import com.example.SecurityApp.dto.PostDTO;
import com.example.SecurityApp.services.PostService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Secured("ROLE_USER")
    public List<PostDTO> getAllPosts() {

        return postService.getAllPosts(); // Add this method in your service
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO input) {
        return postService.createNewPost(input);
    }

//    @PreAuthorize("hasRole('USER','ADMIN')")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') AND hasAnyAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurityService.isOwnerOfPost(#postId")//hash is used to pass the parameter of the method into the preAuth
    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }
}
