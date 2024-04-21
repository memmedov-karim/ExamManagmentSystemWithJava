package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.RegionLoginDto;
import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.service.auth.regionauth.RegionAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionAuthController {
    private final RegionAuthService regionAuthService;

    public RegionAuthController(RegionAuthService regionAuthService) {
        this.regionAuthService = regionAuthService;
    }

    @PostMapping("/region/login")
    public ResponseEntity<String> loginRegion(@RequestBody RegionLoginDto regionLoginDto, HttpServletResponse response){
        return regionAuthService.loginRegion(regionLoginDto,response);
    }
    @GetMapping("/region/logout")
    public ResponseEntity<String> logoutRegion(HttpServletRequest request, HttpServletResponse response){
        return regionAuthService.logoutRegion(request,response);
    }
}
