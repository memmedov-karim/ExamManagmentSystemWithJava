package com.example.ExamManagmentSystem.service.center;

import com.example.ExamManagmentSystem.entity.Center;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CenterService {
    public ResponseEntity<Center> saveCenter(HttpServletRequest request, Center newCenter);
    public ResponseEntity<Long> deleteCenter(HttpServletRequest request,Long centerId);
}
