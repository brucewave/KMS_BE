package com.group3.kindergartenmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDay;
    @Column(nullable = false)
    private Integer height;
    @Column(nullable = false)
    private Integer weight;
    @Column(nullable = false)
    private Boolean gender;
    @Column(nullable = false)
    private String hobby;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private User parent;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private User teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentForChild> comments = new ArrayList<>();
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineReminder> medicineReminders = new ArrayList<>();
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();
}
