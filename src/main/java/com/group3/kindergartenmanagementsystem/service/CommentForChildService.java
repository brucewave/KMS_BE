package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.model.CommentForChild;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.CommentForChildDTO;

import java.util.List;

public interface CommentForChildService {
    List<CommentForChildDTO> getAllCommentByChildId(Integer childId);
    CommentForChildDTO getCommentById(Integer id);
    CommentForChildDTO createNewComment(CommentForChildDTO commentForChildDTO);
    CommentForChildDTO updateCommentById(Integer id, CommentForChildDTO commentForChildDTO);
    String deleteCommentById(Integer id);
}
