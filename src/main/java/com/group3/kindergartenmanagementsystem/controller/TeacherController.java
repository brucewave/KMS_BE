package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.TeacherDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/teacher")
public class TeacherController {
    TeacherService teacherService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TeacherDTO>> getAllTeacher(){
        return ResponseEntity.ok(teacherService.getAllTeacher());
    }
    @GetMapping("/current")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherDTO> getCurrentTeacher(){
        return ResponseEntity.ok(teacherService.getCurrentTeacher());
    }
    @PutMapping("/{teacherId}/addToClass/{classroomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addTeacherToClass(@PathVariable Integer teacherId,
                                                    @PathVariable Integer classroomId){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain; charset=UTF-8");
        return new ResponseEntity<>(teacherService.addTeacherToClass(teacherId, classroomId), headers, HttpStatus.OK);
    }
}
