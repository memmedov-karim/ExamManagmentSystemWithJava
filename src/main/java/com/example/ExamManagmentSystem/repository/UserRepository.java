package com.example.ExamManagmentSystem.repository;

import com.example.ExamManagmentSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public boolean existsUserByName(String name);
    public boolean existsUserByEmail(String email);
    public User findByEmail(String email);
}
