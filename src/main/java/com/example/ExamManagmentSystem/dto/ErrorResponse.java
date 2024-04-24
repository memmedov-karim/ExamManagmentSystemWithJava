package com.example.ExamManagmentSystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    public String getTimestamp() {
        return timestamp.toString();
    }
}
