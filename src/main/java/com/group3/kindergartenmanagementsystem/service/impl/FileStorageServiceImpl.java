package com.group3.kindergartenmanagementsystem.service.impl;

import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import com.group3.kindergartenmanagementsystem.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final Path root = Paths.get(AppConstants.UPLOAD_FOLDER);
    private final Path bin = Paths.get(AppConstants.BIN_FOLDER);
    @Override
    public void init() {
        if (!Files.exists(root)){
            try{
                Files.createDirectory(root);
            } catch (IOException e){
                throw new RuntimeException("Could not initialize folder for upload");
            }
        }
        if (!Files.exists(bin)){
            try{
                Files.createDirectory(bin);
            } catch (IOException e){
                throw new RuntimeException("Could not initialize folder for delete");
            }
        }
    }

    @Override
    public String save(MultipartFile file) {
        String uuid = String.valueOf(UUID.randomUUID());
        String filename = uuid+"."+FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), root.resolve(filename));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return filename;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public String delete(String fileName) {
        Path fileToDelete = root.resolve(fileName);
        try {
            Files.move(fileToDelete, bin.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new RuntimeException("Exception when delete file: "+e.getMessage());
        }
        return "File moved successfully.";
    }
}
