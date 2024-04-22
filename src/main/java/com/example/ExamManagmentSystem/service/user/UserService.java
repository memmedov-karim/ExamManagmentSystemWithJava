package com.example.ExamManagmentSystem.service.user;

import com.example.ExamManagmentSystem.dto.UserRegisterDto;
import com.example.ExamManagmentSystem.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public User saveUser(UserRegisterDto newUser);
    public List<User> allUser();
    public Long deleteUser(Long id);
    public User updateUser(Long id,User newUser);
}
