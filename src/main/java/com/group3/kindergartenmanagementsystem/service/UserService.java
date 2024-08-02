package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser();
    UserDTO getUserById(Integer id);
    UserDTO updateUserById(Integer id, UserDTO userDTO);
    UserDTO createNewUser(UserDTO userDTO);
    void deleteUserById(Integer id);
}