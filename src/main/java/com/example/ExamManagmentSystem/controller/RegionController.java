package com.example.ExamManagmentSystem.controller;

import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.service.region.RegionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegionController {
    private final RegionService regionService;
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }
    //User creat new region with user id
    @PostMapping("/regions")
    public ResponseEntity<Region> saveRegion(HttpServletRequest request, @RequestBody Region newRegion){
        return regionService.saveRegion(request,newRegion);
    }
    //User delete region with user id and region id
    @DeleteMapping("/regions/{regionid}")
    public ResponseEntity<Long> deleteRegion(HttpServletRequest request,@PathVariable Long regionid){
        return regionService.deleteRegion(request, regionid);
    }
    //Fetching all regions which created each user with user id
    @GetMapping("/regions/user")
    public ResponseEntity<List<Region>> allUserRegions(HttpServletRequest request){
        return regionService.allUserRegions(request);
    }
}
