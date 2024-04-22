package com.example.ExamManagmentSystem.service.exam;

import com.example.ExamManagmentSystem.dto.NewExamDto;
import com.example.ExamManagmentSystem.entity.Exam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamService {
    public Exam saveExam(HttpServletRequest request, NewExamDto exam);
    public List<Exam> allRegionExams(HttpServletRequest request);
}
