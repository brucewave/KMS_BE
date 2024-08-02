package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.AddPostDTO;
import com.group3.kindergartenmanagementsystem.payload.PostDTO;
import com.group3.kindergartenmanagementsystem.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    PostService postService;
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPost(){
        return ResponseEntity.ok(postService.getAllPost());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PostMapping
    public ResponseEntity<PostDTO> createNewPost(@ModelAttribute AddPostDTO addPostDTO){
        return new ResponseEntity<>(postService.createNewPost(addPostDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable Integer id,
                                                  @ModelAttribute AddPostDTO addPostDTO){
        return ResponseEntity.ok(postService.updatePostById(id, addPostDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Integer id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Deleted");
    }
}
