package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.service.auth.userauth.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController
public class UserAuthController {
    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response){
        return userAuthService.loginUser(userLoginDto,response);
    }
    @GetMapping("/user/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request,HttpServletResponse response){
        return userAuthService.logoutUser(request,response);
    }
}
