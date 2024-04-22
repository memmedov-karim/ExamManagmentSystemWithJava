package com.example.ExamManagmentSystem.service.auth.userauth;

import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserAuthService {
    public String loginUser(UserLoginDto userLoginDto, HttpServletResponse response);
    public String logoutUser(HttpServletRequest request, HttpServletResponse response);
}
