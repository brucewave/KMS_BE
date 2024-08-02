package com.group3.kindergartenmanagementsystem.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForTeacherDTO {
    private Integer id;
    private Integer attitudeScore;
    private Integer creativeScore;
    private String comment;
    private Integer teacherId;
}
