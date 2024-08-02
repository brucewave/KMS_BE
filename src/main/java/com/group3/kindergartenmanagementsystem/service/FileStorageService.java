package com.group3.kindergartenmanagementsystem.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();
    String save(MultipartFile file);
    Resource load(String filename);
    void deleteAll();
    String delete(String fileName);
}
