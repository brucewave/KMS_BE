package com.group3.kindergartenmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime sendTime;
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;
}
