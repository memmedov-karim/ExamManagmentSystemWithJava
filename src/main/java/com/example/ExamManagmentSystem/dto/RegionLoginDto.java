package com.example.ExamManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegionLoginDto {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message="password is required")
    private String password;
}
