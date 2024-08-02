package com.group3.kindergartenmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPostDTO {
    private Integer id;
    private MultipartFile image;
    private String title;
    private String content;
}
