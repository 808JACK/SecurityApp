package com.example.SecurityApp.utils;


import com.example.SecurityApp.dto.PostDTO;
import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurityService {

    private final PostService postService;
    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO = postService.getPostById(postId);
        return postDTO.getAuthor().getId().equals(user.getId());
    }
}
