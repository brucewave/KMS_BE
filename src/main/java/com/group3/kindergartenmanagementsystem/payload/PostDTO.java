package com.group3.kindergartenmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Integer id;
    private String image;
    private String title;
    private String content;
    private LocalDateTime postDate;
}
