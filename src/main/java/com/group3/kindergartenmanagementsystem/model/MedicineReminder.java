package com.group3.kindergartenmanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String currentStatus;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_id")
    private Child child;
}
