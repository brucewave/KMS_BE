package com.group3.kindergartenmanagementsystem.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
public class MedicineReminderDTO {
    private Integer id;
    private String comment;
    private String currentStatus;
    private Integer childId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
