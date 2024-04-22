package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.RegionLoginDto;
import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.service.auth.regionauth.RegionAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionAuthController {
    private final RegionAuthService regionAuthService;

    @PostMapping("/login")
    public ResponseEntity<String> loginRegion(@RequestBody RegionLoginDto regionLoginDto, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionAuthService.loginRegion(regionLoginDto,response));
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logoutRegion(HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionAuthService.logoutRegion(request,response));
    }
}
