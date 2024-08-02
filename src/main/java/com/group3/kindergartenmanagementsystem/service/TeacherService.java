package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.TeacherDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;

import java.util.List;

public interface TeacherService {
    String addTeacherToClass(Integer teacherId, Integer classroomId);
    TeacherDTO getCurrentTeacher();
    List<TeacherDTO> getAllTeacher();
}
