package com.example.ExamManagmentSystem.repository;

import com.example.ExamManagmentSystem.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Long> {
    public boolean existsByNameAndCreatorId(String name,Long userid);
}
