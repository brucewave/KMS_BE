package com.group3.kindergartenmanagementsystem.payload;

import com.group3.kindergartenmanagementsystem.model.Child;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomDTO {
    private Integer id;
    private String name;
    private Set<Integer> childIds;
    private Integer teacherId;
}
