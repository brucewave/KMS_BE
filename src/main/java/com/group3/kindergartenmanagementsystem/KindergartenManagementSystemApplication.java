package com.group3.kindergartenmanagementsystem;

import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KindergartenManagementSystemApplication implements CommandLineRunner {
    @Resource
    FileStorageService fileStorageService;
    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(KindergartenManagementSystemApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        fileStorageService.init();
    }
}
