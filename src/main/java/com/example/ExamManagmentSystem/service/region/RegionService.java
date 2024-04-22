package com.example.ExamManagmentSystem.service.region;

import com.example.ExamManagmentSystem.dto.NewRegionDto;
import com.example.ExamManagmentSystem.entity.Region;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RegionService {
    public Region saveRegion(HttpServletRequest request, NewRegionDto newRegion);
    public Long deleteRegion(HttpServletRequest request,Long regionid);
    public List<Region> allUserRegions(HttpServletRequest request);
}
