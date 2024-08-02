package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.UserCreateDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.ParentService;
import com.group3.kindergartenmanagementsystem.service.UserService;
import com.group3.kindergartenmanagementsystem.utils.ReceivedRole;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.group3.kindergartenmanagementsystem.service.impl.UserServiceImpl.mapToDTO;
import static com.group3.kindergartenmanagementsystem.service.impl.UserServiceImpl.mapToEntity;

@Service
@AllArgsConstructor
public class ParentServiceImpl implements ParentService {
    ChildRepository childRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;
    ModelMapper mapper;

    @Override
    public UserCreateDTO createNewParent(UserDTO userDTO, ChildDTO childDTO) {
        Role role = roleRepository.findByRoleName(ReceivedRole.getRoleName(ReceivedRole.Parent));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = mapToEntity(userDTO);
        user.setRoles(roleSet);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        User newUser = userRepository.save(user);
        Child child = Child.builder()
                .fullName(childDTO.getFullName())
                .age(LocalDateTime.now().getYear()-childDTO.getBirthDay().getYear())
                .birthDay(childDTO.getBirthDay())
                .height(childDTO.getHeight())
                .weight(childDTO.getWeight())
                .gender(childDTO.getGender())
                .hobby(childDTO.getHobby())
                .parent(newUser)
                .build();
        Child createdChild = childRepository.save(child);
        return UserCreateDTO.builder()
                .userDTO(mapToDTO(newUser))
                .childDTO(mapper.map(createdChild, ChildDTO.class))
                .build();
    }
    @Override
    public List<UserDTO> getAllParent() {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_PARENT");
        roleSet.add(role);
        List<User> users = userRepository.findByRoles(roleSet);
        return users.stream().map(UserServiceImpl::mapToDTO).collect(Collectors.toList());
    }

}
