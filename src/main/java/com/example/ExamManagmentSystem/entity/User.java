package com.example.ExamManagmentSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Region> regions;
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Exam> exams;
}
