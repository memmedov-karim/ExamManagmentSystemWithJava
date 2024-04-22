package com.example.ExamManagmentSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/api/testjava")
    public String calculate(){
        int[] data ={1,2,3,4,5};
        String c;
        if(data[3]==4){
            c = "ok";
        }
        else{
            c = "no";
        }
        return c;
    }
}
