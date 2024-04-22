package com.example.ExamManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NewTicketDto {
    @NotNull(message = "exam value is required")
    private Long exam;
    @NotNull(message = "center value is required")
    private Long center;
    private String clas;
    private String sector;
    private String name;
    private String surname;
    private String father;
    private String room;
    private String place;
    private String phone;
}
