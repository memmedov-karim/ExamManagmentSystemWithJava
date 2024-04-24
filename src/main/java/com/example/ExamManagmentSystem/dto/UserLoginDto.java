package com.example.ExamManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Email is required")
    private String password;
}
