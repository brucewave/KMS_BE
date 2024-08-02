package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.CommentForChildDTO;
import com.group3.kindergartenmanagementsystem.service.CommentForChildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/childComment")
public class CommentForChildController {
    CommentForChildService commentForChildService;

    public CommentForChildController(CommentForChildService commentForChildService) {
        this.commentForChildService = commentForChildService;
    }

    @GetMapping("/child/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<List<CommentForChildDTO>> getAllCommentByChildId(@PathVariable Integer id){
        return ResponseEntity.ok(commentForChildService.getAllCommentByChildId(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<CommentForChildDTO> getCommentById(@PathVariable Integer id){
        return ResponseEntity.ok(commentForChildService.getCommentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CommentForChildDTO> createNewComment(@RequestBody CommentForChildDTO comment){
        return new ResponseEntity<>(commentForChildService.createNewComment(comment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CommentForChildDTO> updateCommentById(@PathVariable Integer id,
                                                                @RequestBody CommentForChildDTO comment){
        return ResponseEntity.ok(commentForChildService.updateCommentById(id, comment));
    }

    @DeleteMapping ("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteCommentById(@PathVariable Integer id){
        return ResponseEntity.ok(commentForChildService.deleteCommentById(id));
    }
}
