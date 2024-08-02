package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.ClassroomDTO;
import com.group3.kindergartenmanagementsystem.service.ClassroomService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {
    ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<ClassroomDTO>> getAllClassroom(){
        return ResponseEntity.ok(classroomService.getAllClassroom());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Integer id){
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @GetMapping("/children/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<ClassroomDTO> getClassroomByChildId(@PathVariable Integer id){
        return ResponseEntity.ok(classroomService.getClassroomByChildId(id));
    }

    @GetMapping("/teacher/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> getClassroomByTeacherId(@PathVariable Integer id){
        return ResponseEntity.ok(classroomService.getClassroomByTeacherId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> createNewClassroom(@RequestBody ClassroomDTO classroomDTO){
        return new ResponseEntity<>(classroomService.createNewClassroom(classroomDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> updateClassroomById(@PathVariable Integer id,
                                                            @RequestBody ClassroomDTO classroomDTO){
        return ResponseEntity.ok(classroomService.updateClassroomById(id, classroomDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteClassroomById(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain; charset=UTF-8");
        return new ResponseEntity<>(classroomService.deleteClassroomById(id), headers, HttpStatus.OK);
    }
}
