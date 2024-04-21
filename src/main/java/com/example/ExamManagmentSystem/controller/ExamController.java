package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.service.center.CenterService;
import com.example.ExamManagmentSystem.service.exam.ExamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExamController {

    private final ExamService examService;
    public ExamController(ExamService examService) {
        this.examService = examService;
    }
    //User creat new exam with user id
    @PostMapping("/exams")
    public ResponseEntity<Exam> saveExam(HttpServletRequest request, @RequestBody Exam newExam){
        return examService.saveExam(request,newExam);
    }
    //Fetch all exams which created each user for region id
    @GetMapping("/exams/region")
    public ResponseEntity<List<Exam>> allRegionExams(HttpServletRequest request){
        return examService.allRegionExams(request);
    }
}
