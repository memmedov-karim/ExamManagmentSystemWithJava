package com.example.ExamManagmentSystem.repository;

import com.example.ExamManagmentSystem.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
    public boolean existsByNameAndCreatorId(String name, Long creator_id);
    public Region findByUsername(String username);

}
