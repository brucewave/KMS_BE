package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ForbiddenAccessException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.CommentForChild;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.CommentForChildDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.CommentForChildRepository;
import com.group3.kindergartenmanagementsystem.service.CommentForChildService;
import com.group3.kindergartenmanagementsystem.service.CommentForTeacherService;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentForChildServiceImpl implements CommentForChildService {
    CommentForChildRepository commentForChildRepository;
    ChildRepository childRepository;
    SecurityService securityService;
    ModelMapper mapper;

    @Override
    public List<CommentForChildDTO> getAllCommentByChildId(Integer childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(()-> new ResourceNotFoundException("Child", "id", childId));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        List<CommentForChild> comments = commentForChildRepository.findAllByChild(child);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentForChildDTO getCommentById(Integer id) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        if (!securityService.isParentOrTeacherOfChild(comment.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        return mapToDTO(comment);
    }

    @Override
    public CommentForChildDTO createNewComment(CommentForChildDTO commentForChildDTO) {
        Child child = childRepository.findById(commentForChildDTO.getChildId()).orElseThrow(
                () -> new ResourceNotFoundException("Child", "id", commentForChildDTO.getChildId()));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        CommentForChild comment = mapToEntity(commentForChildDTO);
        comment.setChild(child);
        CommentForChild createdComment = commentForChildRepository.save(comment);
        return mapToDTO(createdComment);
    }

    @Override
    public CommentForChildDTO updateCommentById(Integer id, CommentForChildDTO commentForChildDTO) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        Child child = childRepository.findById(commentForChildDTO.getChildId()).orElseThrow(() -> new ResourceNotFoundException("Child", "id", id));
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        comment.setComment(commentForChildDTO.getComment());
        comment.setPostMonth(commentForChildDTO.getPostMonth());
        comment.setChild(child);
        CommentForChild updatedComment = commentForChildRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public String deleteCommentById(Integer id) {
        CommentForChild comment = commentForChildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for child", "id", id));
        if (!securityService.isParentOrTeacherOfChild(comment.getChild()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this child");
        commentForChildRepository.delete(comment);
        return "Comment deleted successfully";
    }

    private CommentForChildDTO mapToDTO(CommentForChild comment){
        return CommentForChildDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .childId(comment.getChild().getId())
                .postMonth(comment.getPostMonth())
                .build();
    }

    private CommentForChild mapToEntity(CommentForChildDTO commentForChildDTO){
        return CommentForChild.builder()
                .comment(commentForChildDTO.getComment())
                .postMonth(commentForChildDTO.getPostMonth())
                .build();
    }
}
