package com.example.ExamManagmentSystem.entity;

import com.example.ExamManagmentSystem.utils.PasswordGenerator;
import com.example.ExamManagmentSystem.utils.UtisGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="region_id",nullable = false)
//    @JsonIgnore
    @JsonBackReference("region-reference")
    private Region region;
    @ManyToOne
    @JoinColumn(name="exam_id",nullable = false)
//    @JsonIgnore
    @JsonBackReference("exam-reference")
    private Exam exam;
    @ManyToOne
    @JoinColumn(name="center_id",nullable = false)
//    @JsonIgnore
    @JsonBackReference("center-reference")
    private Center center;
    @Column(nullable = false)
    private String clas;
    @Column(nullable = false)
    private  String sector;
    private String fenn;
    private String name="";

    private String surname="";

    private String father="";

    private String room;
    private String place;
    private String phone;
    private String utis;

}
