package com.example.ExamManagmentSystem.dto;

import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.entity.Region;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RegionTicketDto {
    private Long id;
    private Long regionid;
    private Long examid;
    private String examname;
    private String examtitle;
    private String examdateandtime;
    private Long centerid;
    private String centername;
    private String centercode;
    private String clas;
    private String fenn;
    private  String sector;
    private String name;
    private String surname;
    private String father;
    private String room;
    private String place;
    private String phone;
    private String utis;

}
