package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ForbiddenAccessException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.CommentForTeacher;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.CommentForTeacherDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.CommentForTeacherRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
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
public class CommentForTeacherServiceImpl implements CommentForTeacherService {
    CommentForTeacherRepository commentForTeacherRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    SecurityService securityService;
    ChildRepository childRepository;
    ModelMapper mapper;

    @Override
    public List<CommentForTeacherDTO> getAllCommentByTeacherId(Integer teacherId) {
        if (securityService.compareRole("ROLE_TEACHER")){
            if (!securityService.getCurrentUser().getId().equals(teacherId))
                throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                        "User with username: "+securityService.getUsername()+" can't access to these comment");
        }
        User teacher = userRepository.findById(teacherId).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", teacherId)
        );
        List<CommentForTeacher> comments = commentForTeacherRepository.findAllByTeacher(teacher);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentForTeacherDTO getCommentById(Integer id) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment for teacher", "id", id));
        if (securityService.compareRole("ROLE_TEACHER")){
            if (!securityService.getCurrentUser().getId().equals(comment.getTeacher().getId()))
                throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                        "User with username: "+securityService.getUsername()+" can't access to these comment");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentForTeacherDTO createNewComment(CommentForTeacherDTO commentForTeacherDTO) {
        CommentForTeacher comment = mapToEntity(commentForTeacherDTO);
        User teacher = userRepository.findById(commentForTeacherDTO.getTeacherId()).orElseThrow(()-> new ResourceNotFoundException("Teacher", "id", commentForTeacherDTO.getTeacherId()));
        Child child = childRepository.findByParent(securityService.getCurrentUser());
        if (!child.getTeacher().getId().equals(teacher.getId()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this teacher");
        comment.setTeacher(teacher);
        CommentForTeacher newComment = commentForTeacherRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public CommentForTeacherDTO updateCommentById(Integer id, CommentForTeacherDTO commentForTeacherDTO) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment for teacher", "id", id));
        User teacher = userRepository.findById(commentForTeacherDTO.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", commentForTeacherDTO.getTeacherId()));
        Child child = childRepository.findByParent(securityService.getCurrentUser());
        if (!child.getTeacher().getId().equals(teacher.getId()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this teacher");
        comment.setComment(commentForTeacherDTO.getComment());
        comment.setAttitudeScore(commentForTeacherDTO.getAttitudeScore());
        comment.setCreativeScore(commentForTeacherDTO.getCreativeScore());
        comment.setTeacher(teacher);
        CommentForTeacher updatedComment = commentForTeacherRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public String deleteCommendById(Integer id) {
        CommentForTeacher comment = commentForTeacherRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment for teacher", "id", id));
        Child child = childRepository.findByParent(securityService.getCurrentUser());
        if (!child.getTeacher().getId().equals(comment.getTeacher().getId()))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST,
                    "User with username: "+securityService.getUsername()+" can't access to this teacher");
        commentForTeacherRepository.delete(comment);
        return "Delete comment successfully";
    }

    private CommentForTeacherDTO mapToDTO(CommentForTeacher comment){
        CommentForTeacherDTO commentForTeacherDTO = mapper.map(comment, CommentForTeacherDTO.class);
        commentForTeacherDTO.setTeacherId(comment.getTeacher().getId());
        return commentForTeacherDTO;
    }

    private CommentForTeacher mapToEntity(CommentForTeacherDTO comment){
        User teacher = userRepository.findById(comment.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", comment.getTeacherId()));
        CommentForTeacher commentForTeacher = mapper.map(comment, CommentForTeacher.class);
        commentForTeacher.setTeacher(teacher);
        return commentForTeacher;
    }
}
