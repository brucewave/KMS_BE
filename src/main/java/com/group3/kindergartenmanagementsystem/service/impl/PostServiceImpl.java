package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Post;
import com.group3.kindergartenmanagementsystem.payload.AddPostDTO;
import com.group3.kindergartenmanagementsystem.payload.PostDTO;
import com.group3.kindergartenmanagementsystem.repository.PostRepository;
import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import com.group3.kindergartenmanagementsystem.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    FileStorageService fileStorageService;
    ModelMapper mapper;
    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        return mapToDTO(post);
    }

    @Override
    public PostDTO createNewPost(AddPostDTO addPostDTO) {
        String fileName = null;
        if (addPostDTO.getImage() != null)
            fileName = fileStorageService.save(addPostDTO.getImage());
        Post post = new Post();
        post.setPostDate(LocalDateTime.now());
        post.setTitle(addPostDTO.getTitle());
        post.setContent(addPostDTO.getContent());
        post.setImage(fileName);
        Post createdPost = postRepository.save(post);
        return mapToDTO(createdPost);
    }

    @Override
    public PostDTO updatePostById(Integer postId, AddPostDTO addPostDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        String fileName = null;
        if (addPostDTO.getImage() != null){
            fileStorageService.delete(post.getImage());
            fileName = fileStorageService.save(addPostDTO.getImage());
        }
        post.setTitle(addPostDTO.getTitle());
        post.setContent(addPostDTO.getContent());
        post.setImage(fileName);
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        if (post.getImage() != null)
            fileStorageService.delete(post.getImage());
        postRepository.delete(post);
    }

    private PostDTO mapToDTO(Post post){
        return mapper.map(post, PostDTO.class);
    }
}
