package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.NewCenterDto;
import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.service.center.CenterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/centers/region")
public class CenterController {

    private final CenterService centerService;
    //Region creat new center with region id
    @PostMapping()
    public ResponseEntity<Center> saveCenter(HttpServletRequest request, @RequestBody NewCenterDto newCenter){
        return ResponseEntity.status(HttpStatus.CREATED).body(centerService.saveCenter(request,newCenter));
    }
    //Region delete center with centerid
    @DeleteMapping("/{centerId}")
    public ResponseEntity<Long> deleteCenter(HttpServletRequest request,@PathVariable Long centerId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(centerService.deleteCenter(request,centerId));
    }
}
