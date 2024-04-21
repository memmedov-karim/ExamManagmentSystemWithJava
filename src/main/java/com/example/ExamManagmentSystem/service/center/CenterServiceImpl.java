package com.example.ExamManagmentSystem.service.center;

import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.repository.CenterRepository;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.validator.InputValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final RegionRepository regionRepository;
    private final InputValidator inputValidator;
    private final JsonWebTokenService jsonWebTokenService;
    public CenterServiceImpl(CenterRepository centerRepository, RegionRepository regionRepository, InputValidator inputValidator, JsonWebTokenService jsonWebTokenService){
        this.centerRepository = centerRepository;
        this.regionRepository = regionRepository;
        this.inputValidator = inputValidator;
        this.jsonWebTokenService = jsonWebTokenService;
    }
    @Override
    public ResponseEntity<Center> saveCenter(HttpServletRequest request, Center newCenter){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id: "+regionid));
//        System.out.println(newCenter.getName()+":"+newCenter.getCity()+":"+newCenter.getCode());
        if(!inputValidator.checkinputemtynull(newCenter.getName(),newCenter.getCity(),newCenter.getCode())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name,city and code is required");
        }
        if(centerRepository.existsByNameAndRegionId(newCenter.getName(), regionid)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This name: "+newCenter.getName()+" already exsist");
        }
        newCenter.setRegion(exsistingRegion);
        Center savedCenter = centerRepository.save(newCenter);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedCenter);
    }

    @Override
    public ResponseEntity<Long> deleteCenter(HttpServletRequest request,Long centerId){
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id:"+regionId));
        Center exsistingCenter = centerRepository.findById(centerId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not center with this id:"+centerId));
        if(!Objects.equals(exsistingCenter.getRegion().getId(), regionId)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You can not delete this center");
        }
        centerRepository.deleteById(centerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(exsistingCenter.getId());
    }
}
