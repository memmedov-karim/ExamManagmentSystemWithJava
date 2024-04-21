package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.service.center.CenterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CenterController {

    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }
    //Region creat new center with region id
    @PostMapping("/centers/region")
    public ResponseEntity<Center> saveCenter(HttpServletRequest request, @RequestBody Center newCenter){
        return centerService.saveCenter(request,newCenter);
    }
    //Region delete center with centerid
    @DeleteMapping("/centers/region/{centerId}")
    public ResponseEntity<Long> deleteCenter(HttpServletRequest request,@PathVariable Long centerId){
        return centerService.deleteCenter(request,centerId);
    }
}
