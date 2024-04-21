package com.example.ExamManagmentSystem.service.region;

import com.example.ExamManagmentSystem.entity.Region;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegionService {
    public ResponseEntity<Region> saveRegion(HttpServletRequest request, Region newRegion);
    public ResponseEntity<Long> deleteRegion(HttpServletRequest request,Long regionid);
    public ResponseEntity<List<Region>> allUserRegions(HttpServletRequest request);
}
