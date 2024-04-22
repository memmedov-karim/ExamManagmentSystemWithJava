package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.UserLoginDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.service.auth.userauth.UserAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserAuthController {
    private final UserAuthService userAuthService;
    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userAuthService.loginUser(userLoginDto,response));
    }
    @GetMapping("logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request,HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userAuthService.logoutUser(request,response));
    }
}
