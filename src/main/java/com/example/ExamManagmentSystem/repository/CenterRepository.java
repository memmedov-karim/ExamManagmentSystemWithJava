package com.example.ExamManagmentSystem.repository;

import com.example.ExamManagmentSystem.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center,Long> {
    public boolean existsByNameAndRegionId(String name,Long regionid);
}
