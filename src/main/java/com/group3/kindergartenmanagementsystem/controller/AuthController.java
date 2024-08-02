package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.JwtAuthResponse;
import com.group3.kindergartenmanagementsystem.payload.LoginDto;
import com.group3.kindergartenmanagementsystem.payload.LoginPayload;
import com.group3.kindergartenmanagementsystem.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    AuthService authService;
    @PostMapping(value = {"/signin", "/login"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        LoginPayload payload = authService.login(loginDto);
        return ResponseEntity.ok(JwtAuthResponse.builder()
                .accessToken(payload.getAccessToken())
                .userId(payload.getUserId())
                .build());
    }
}
