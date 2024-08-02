package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.AddChildToObjectDTO;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.service.ChildService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {
    ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChildDTO>> getAllChild(){
        return ResponseEntity.ok(childService.getAllChild());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<ChildDTO> getChildById(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getChildById(id));
    }

    @GetMapping("/parent/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<ChildDTO> getChildByParentId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getChildByParentId(id));
    }

    @GetMapping("/classroom/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<List<ChildDTO>> getAllChildByClassroomId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getAllChildByClassroom(id));
    }

    @GetMapping("/teacher/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<List<ChildDTO>> getAllChildByTeacherId(@PathVariable Integer id){
        return ResponseEntity.ok(childService.getAllChildByTeacher(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PARENT', 'TEACHER')")
    public ResponseEntity<ChildDTO> updateChildById(@PathVariable Integer id,
                                                    @RequestBody ChildDTO childDTO){
        return ResponseEntity.ok(childService.updateChildById(id, childDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteChildById(@PathVariable Integer id){
        return ResponseEntity.ok(childService.deleteChildById(id));
    }

    @PutMapping("/add/classroom")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChildDTO>> addChildToClassroom(@RequestBody AddChildToObjectDTO addChildToObjectDTO){
        return ResponseEntity.ok(childService.addChildToClassroom(addChildToObjectDTO.getChildIds(),addChildToObjectDTO.getObjectId()));
    }
}
