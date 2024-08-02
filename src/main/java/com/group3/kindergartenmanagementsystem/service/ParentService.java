package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.ChildDTO;
import com.group3.kindergartenmanagementsystem.payload.UserCreateDTO;
import com.group3.kindergartenmanagementsystem.payload.UserDTO;

import java.util.List;

public interface ParentService {
    UserCreateDTO createNewParent(UserDTO userDTO, ChildDTO childDTO);
    List<UserDTO> getAllParent();
}
