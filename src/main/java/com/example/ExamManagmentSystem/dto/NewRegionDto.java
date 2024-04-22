package com.example.ExamManagmentSystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRegionDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "username is required")
    private String username;
}
