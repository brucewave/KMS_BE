package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.CommentForChild;
import com.group3.kindergartenmanagementsystem.model.CommentForTeacher;
import com.group3.kindergartenmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentForTeacherRepository extends JpaRepository<CommentForTeacher, Integer> {
    List<CommentForTeacher> findAllByTeacher(User teacher);
}
