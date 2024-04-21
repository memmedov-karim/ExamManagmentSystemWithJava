package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    //New user registration
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User newUser){
        return userService.saveUser(newUser);
    }
    //Fetching all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> allUser(){
        return userService.allUser();
    }
    //Deleting user for user id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    //Update user for user id
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody User newUser){
        return userService.updateUser(id,newUser);
    }
}
