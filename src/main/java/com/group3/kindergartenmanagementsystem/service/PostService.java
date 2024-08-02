package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.AddPostDTO;
import com.group3.kindergartenmanagementsystem.payload.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO getPostById(Integer postId);
    PostDTO createNewPost(AddPostDTO addPostDTO);
    PostDTO updatePostById(Integer postId, AddPostDTO addPostDTO);
    void deletePostById(Integer postId);
}
