package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.UserRegisterDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    //New user registration
    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody UserRegisterDto newUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(newUser));
    }
    //Fetching all users
    @GetMapping()
    public ResponseEntity<List<User>> allUser(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.allUser());
    }
    //Deleting user for user id
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.deleteUser(id));
    }
    //Update user for user id
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User newUser){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(id,newUser));
    }
}
