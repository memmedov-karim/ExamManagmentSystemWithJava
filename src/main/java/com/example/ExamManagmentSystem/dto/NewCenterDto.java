package com.example.ExamManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCenterDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "code is required")
    private String code;
}
