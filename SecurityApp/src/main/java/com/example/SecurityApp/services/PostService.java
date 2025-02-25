package com.example.SecurityApp.services;

import com.example.SecurityApp.dto.PostDTO;

import java.util.List;

public interface PostService {


    PostDTO createNewPost(PostDTO input);

    PostDTO getPostById(Long postId);

    List<PostDTO> getAllPosts();
}
