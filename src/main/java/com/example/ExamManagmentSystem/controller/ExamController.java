package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.NewExamDto;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.service.center.CenterService;
import com.example.ExamManagmentSystem.service.exam.ExamService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;
    //User creat new exam with user id
    @PostMapping()
    public ResponseEntity<Exam> saveExam(HttpServletRequest request, @RequestBody NewExamDto newExam){
        return ResponseEntity.status(HttpStatus.CREATED).body(examService.saveExam(request,newExam));
    }
    //Fetch all exams which created each user for region id
    @GetMapping("/region")
    public ResponseEntity<List<Exam>> allRegionExams(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(examService.allRegionExams(request));
    }
}
