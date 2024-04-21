package com.example.ExamManagmentSystem.service.exam;

import com.example.ExamManagmentSystem.entity.Exam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamService {
    public ResponseEntity<Exam> saveExam(HttpServletRequest request, Exam exam);
    public ResponseEntity<List<Exam>> allRegionExams(HttpServletRequest request);
}
