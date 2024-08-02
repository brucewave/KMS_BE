package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.APIException;
import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Classroom;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.ClassroomRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.UserService;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    ChildRepository childRepository;
    ClassroomRepository classroomRepository;
    ModelMapper mapper;

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(UserServiceImpl::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDTO(user);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO updateUserById(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO createNewUser(UserDTO userDTO) {
        Role role = roleRepository.findByRoleName(ReceivedRole.getRoleName(ReceivedRole.Teacher));
            Set<Role> roles = new HashSet<>();
        roles.add(role);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .password(encoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
        User newTeacher = userRepository.save(user);
        return mapToDTO(newTeacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User", "id", id));
        if(user.getRoles().contains(roleRepository.findByRoleName("ROLE_PARENT"))){
            Child child = childRepository.findByParent(user);
            child.setTeacher(null);
            childRepository.delete(child);
        }
        if (user.getRoles().contains(roleRepository.findByRoleName("ROLE_TEACHER"))){
            Classroom classroom = classroomRepository.findByTeacher(user);
            if (classroom != null){
                classroom.setTeacher(null);
                classroomRepository.save(classroom);
            }
            List<Integer> childIds = childRepository.findAllIdByTeacherId(user.getId());
            childRepository.updateTeacherForChild(null, childIds);
        }
        for (Role role : user.getRoles())
            role.getUsers().remove(user);
        userRepository.delete(user);
    }

    static UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }

    static User mapToEntity(UserDTO userDTO) {
        return User.builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .build();
    }
}
