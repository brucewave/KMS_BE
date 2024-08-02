package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.exception.ResourceNotFoundException;
import com.group3.kindergartenmanagementsystem.model.User;
import com.group3.kindergartenmanagementsystem.payload.LoginDto;
import com.group3.kindergartenmanagementsystem.payload.LoginPayload;
import com.group3.kindergartenmanagementsystem.repository.UserRepository;
import com.group3.kindergartenmanagementsystem.security.JwtProvider;
import com.group3.kindergartenmanagementsystem.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    JwtProvider jwtProvider;
    @Override
    public LoginPayload login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumberOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        String username = authentication.getName();
        User user = userRepository.findByPhoneNumberOrEmail(username, username).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with phone number or email: "+username));
        return LoginPayload.builder()
                .accessToken(token)
                .userId(user.getId())
                .build();
    }
}
