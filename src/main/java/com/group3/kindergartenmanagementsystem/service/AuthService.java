package com.group3.kindergartenmanagementsystem.service;

import com.group3.kindergartenmanagementsystem.payload.LoginDto;
import com.group3.kindergartenmanagementsystem.payload.LoginPayload;

public interface AuthService {
    LoginPayload login(LoginDto loginDto);
}
