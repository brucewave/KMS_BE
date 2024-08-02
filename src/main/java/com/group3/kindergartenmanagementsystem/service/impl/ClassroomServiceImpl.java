package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ForbiddenAccessException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.ClassroomDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ClassroomService;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    ClassroomRepository classroomRepository;
    ChildRepository childRepository;
    SecurityService securityService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    ModelMapper mapper;

    @Override
    public List<ClassroomDTO> getAllClassroom() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO getClassroomById(Integer id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        return mapToDTO(classroom);
    }

    @Override
    public ClassroomDTO getClassroomByChildId(Integer childId) {
        Child child = childRepository.findById(childId).orElseThrow(
                ()-> new ResourceNotFoundException("Child", "id", childId)
        );
        if (!securityService.isParentOrTeacherOfChild(child))
            throw new ForbiddenAccessException(HttpStatus.BAD_REQUEST, "User with username: "+securityService.getUsername()
            +" can't access to this child");
        return mapToDTO(child.getClassroom());
    }

    @Override
    public ClassroomDTO getClassroomByTeacherId(Integer teacherId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", teacherId)
        );
        if(!teacher.getRoles().contains(roleRepository.findByRoleName(ReceivedRole.getRoleName(ReceivedRole.Teacher))))
            throw new APIException(HttpStatus.BAD_REQUEST, "This id is not belong to teacher");
        Classroom classrooms = classroomRepository.findByTeacher(teacher);
        return mapToDTO(classrooms);
    }

    @Override
    public ClassroomDTO createNewClassroom(ClassroomDTO classroomDTO) {
        Set<Child> children = new HashSet<>();
        Classroom classroom = new Classroom(classroomDTO.getId(), classroomDTO.getName(), children, null);
        Classroom newClassroom = classroomRepository.save(classroom);
        return mapToDTO(newClassroom);
    }

    @Override
    public ClassroomDTO updateClassroomById(Integer id, ClassroomDTO classroomDTO) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        Set<Child> children = childRepository.findByIds(classroomDTO.getChildIds());
        User teacher = userRepository.findById(classroomDTO.getTeacherId()).orElseThrow(
                ()-> new ResourceNotFoundException("Teacher", "id", classroomDTO.getTeacherId())
        );
        classroom.setChildren(children);
        classroom.setName(classroomDTO.getName());
        classroom.setTeacher(teacher);
        Classroom updatedClass = classroomRepository.save(classroom);
        return mapToDTO(updatedClass);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String deleteClassroomById(Integer id) {
        Classroom classroom = classroomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        classroomRepository.disassociateChildrenAndTeacher(id);
        childRepository.setClassAndTeacherToNull(id);
        classroomRepository.deleteById(id);
        return "Delete classroom successfully";
    }

    private ClassroomDTO mapToDTO(Classroom classroom){
        Set<Integer> childIds = classroom.getChildren().stream().map(Child::getId).collect(Collectors.toSet());
        ClassroomDTO classroomDTO = ClassroomDTO.builder()
                .id(classroom.getId())
                .name(classroom.getName())
                .childIds(childIds)
                .build();
        if (classroom.getTeacher() != null)
            classroomDTO.setTeacherId(classroom.getTeacher().getId());
        return classroomDTO;
    }

    private Classroom mapToEntity(ClassroomDTO classroomDTO){
        return mapper.map(classroomDTO, Classroom.class);
    }
}
