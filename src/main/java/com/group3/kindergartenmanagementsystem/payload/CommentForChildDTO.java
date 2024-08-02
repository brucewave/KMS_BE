package com.group3.kindergartenmanagementsystem.payload;

import com.group3.kindergartenmanagementsystem.model.Child;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForChildDTO {
    private Integer id;
    private String comment;
    private Integer postMonth;
    private Integer childId;
}
