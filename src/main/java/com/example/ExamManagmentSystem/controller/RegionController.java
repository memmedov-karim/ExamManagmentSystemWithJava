package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.dto.NewRegionDto;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.service.region.RegionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;
    //User creat new region with user id
    @PostMapping()
    public ResponseEntity<Region> saveRegion(HttpServletRequest request, @RequestBody NewRegionDto newRegion){
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.saveRegion(request,newRegion));
    }
    //User delete region with user id and region id
    @DeleteMapping("/{regionid}")
    public ResponseEntity<Long> deleteRegion(HttpServletRequest request,@PathVariable Long regionid){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionService.deleteRegion(request, regionid));
    }
    //Fetching all regions which created each user with user id
    @GetMapping("/user")
    public ResponseEntity<List<Region>> allUserRegions(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionService.allUserRegions(request));
    }
}
