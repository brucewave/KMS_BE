package com.group3.kindergartenmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddAlbumDTO {
    private Integer id;
    private MultipartFile image;
    private Integer childId;
}
