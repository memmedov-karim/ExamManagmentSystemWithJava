package com.example.ExamManagmentSystem.service.center;

import com.example.ExamManagmentSystem.dto.NewCenterDto;
import com.example.ExamManagmentSystem.entity.Center;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface CenterService {
    public Center saveCenter(HttpServletRequest request, NewCenterDto newCenter);
    public Long deleteCenter(HttpServletRequest request,Long centerId);
}
