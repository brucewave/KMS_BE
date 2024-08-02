package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.model.Child;
import com.group3.kindergartenmanagementsystem.model.Role;
import com.group3.kindergartenmanagementsystem.model.User;

import java.util.Set;

public interface SecurityService {
    boolean isParentOrTeacherOfChild(Child child);
    String getUsername();
    boolean compareRole(String roleName);
    User getCurrentUser();
}
