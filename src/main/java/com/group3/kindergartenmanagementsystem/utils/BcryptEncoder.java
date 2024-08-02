package com.group3.kindergartenmanagementsystem.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Matkhau28@"));
    }
}
