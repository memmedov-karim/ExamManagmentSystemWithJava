package com.example.ExamManagmentSystem.service.auth.regionauth;

import com.example.ExamManagmentSystem.dto.RegionLoginDto;
import com.example.ExamManagmentSystem.dto.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface RegionAuthService {
    public String loginRegion(RegionLoginDto regionLoginDto, HttpServletResponse response);
    public String logoutRegion(HttpServletRequest request, HttpServletResponse response);
}
