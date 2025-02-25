package com.example.SecurityApp.services;

import com.example.SecurityApp.dto.PostDTO;
import com.example.SecurityApp.entities.PostEntity;
import com.example.SecurityApp.entities.User;
import com.example.SecurityApp.exceptions.ResourceNotFoundException;
import com.example.SecurityApp.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
@ToString
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO createNewPost(PostDTO input) {
        PostEntity postEntity = modelMapper.map(input,PostEntity.class);
        return modelMapper.map(postRepo.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {

        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      we are logging here like which user  is trying to get the post
        log.info("user {}",user);
        PostEntity postEntity = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post with this " + postId + " not found"));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return  postRepo
                .findAll()
                .stream()
                .map(postEntity1 -> modelMapper.map(postEntity1, PostDTO.class))
                .collect(Collectors.toList());
    }
}
