package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.repository.ChildRepository;
import com.group3.kindergartenmanagementsystem.repository.RoleRepository;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    ChildRepository childRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    @Override
    public boolean isParentOrTeacherOfChild(Child child) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumberOrEmail(username, username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User not found with username: "+username));
        Role parentRole = roleRepository.findByRoleName("ROLE_PARENT");
        if (user.getRoles().contains(parentRole))
            return Objects.equals(child.getParent().getId(), user.getId());
        Role teacherRole = roleRepository.findByRoleName("ROLE_TEACHER");
        if (user.getRoles().contains(teacherRole))
            return Objects.equals(child.getTeacher().getId(), user.getId());
        return false;
    }

    @Override
    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public boolean compareRole(String roleName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumberOrEmail(username, username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User not found with username: "+username));
        Role role = roleRepository.findByRoleName(roleName);
        return user.getRoles().contains(role);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByPhoneNumberOrEmail(username, username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User not found with username: "+username));
    }

}
