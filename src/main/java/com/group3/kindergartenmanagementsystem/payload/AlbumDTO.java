package com.group3.kindergartenmanagementsystem.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AlbumDTO {
    private Integer id;
    private LocalDateTime postedTime;
    private String image;
    private Integer childId;
}
