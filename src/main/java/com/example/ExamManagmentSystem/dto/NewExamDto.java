package com.example.ExamManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewExamDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "dateandtime is required")
    private String dateandtime;
}
