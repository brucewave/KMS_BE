package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.TeacherDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import com.group3.kindergartenmanagementsystem.service.TeacherService;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    ChildRepository childRepository;
    ClassroomRepository classroomRepository;
    RoleRepository roleRepository;
    SecurityService securityService;
    UserRepository userRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addTeacherToClass(Integer teacherId, Integer classroomId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", teacherId)
        );
        Classroom oldClass = classroomRepository.findByTeacher(teacher);
        if (oldClass != null){
            oldClass.setTeacher(null);
            classroomRepository.save(oldClass);
        }
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow(()-> new ResourceNotFoundException("Classroom", "id", classroomId));
        classroom.setTeacher(teacher);
        classroomRepository.save(classroom);
        List<Integer> children = childRepository.findAllIdByClassroomId(classroomId);
        if(!teacher.getRoles().contains(roleRepository.findByRoleName(ReceivedRole.getRoleName(ReceivedRole.Teacher))))
            throw new APIException(HttpStatus.BAD_REQUEST, "This id is not belong to teacher");
        childRepository.updateTeacherForChild(teacherId, children);
        return String.format("Add teacher %s to %s", teacher.getFullName(), classroom.getName());
    }

    @Override
    public TeacherDTO getCurrentTeacher() {
        User user = securityService.getCurrentUser();
        return mapToDTO(user);
    }

    @Override
    public List<TeacherDTO> getAllTeacher() {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_TEACHER");
        roleSet.add(role);
        List<User> users = userRepository.findByRoles(roleSet);
        return users.stream().map(
                this::mapToDTO
        ).collect(Collectors.toList());
    }

    private TeacherDTO mapToDTO(User user){
        Classroom classroom = classroomRepository.findByTeacher(user);
        Integer classroomId = null;
        if (classroom != null)
            classroomId = classroom.getId();
        return TeacherDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .address(user.getAddress())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .classroomIds(classroomId)
                .build();
    }
}
