package com.group3.kindergartenmanagementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private Integer classroomIds;
}
