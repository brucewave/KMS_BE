package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.CommentForTeacherDTO;

import java.util.List;

public interface CommentForTeacherService {
    List<CommentForTeacherDTO> getAllCommentByTeacherId(Integer teacherId);
    CommentForTeacherDTO getCommentById(Integer teacherId);
    CommentForTeacherDTO createNewComment(CommentForTeacherDTO commentForTeacherDTO);
    CommentForTeacherDTO updateCommentById(Integer id, CommentForTeacherDTO commentForTeacherDTO);
    String deleteCommendById(Integer id);
}
