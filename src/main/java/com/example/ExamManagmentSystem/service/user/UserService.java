package com.example.ExamManagmentSystem.service.user;

import com.example.ExamManagmentSystem.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<User> saveUser(User newUser);
    public ResponseEntity<List<User>> allUser();
    public ResponseEntity<Long> deleteUser(Long id);
    public ResponseEntity<User> updateUser(Long id,User newUser);
}
