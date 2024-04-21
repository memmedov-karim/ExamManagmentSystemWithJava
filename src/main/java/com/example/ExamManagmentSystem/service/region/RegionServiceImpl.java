package com.example.ExamManagmentSystem.service.region;

import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.validator.InputValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class RegionServiceImpl implements RegionService {
    private final UserRepository userRepository;
    private final JsonWebTokenService jsonWebTokenService;
    private final InputValidator inputValidator;
    private final RegionRepository regionRepository;
    public RegionServiceImpl(UserRepository userRepository, JsonWebTokenService jsonWebTokenService, InputValidator inputValidator, RegionRepository regionRepository){
        this.userRepository = userRepository;
        this.jsonWebTokenService = jsonWebTokenService;
        this.inputValidator = inputValidator;
        this.regionRepository = regionRepository;
    }
    @Override
    public ResponseEntity<Region> saveRegion(HttpServletRequest request, Region newRegion){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id: " + userid));
        if(!inputValidator.checkinputemtynull(newRegion.getName(),newRegion.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name,username is required");
        }
        if(regionRepository.existsByNameAndCreatorId(newRegion.getName(), userid)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This name: "+newRegion.getName()+" already used");
        }
        newRegion.setCreator(exsistingUser);
        Region savedRegion = regionRepository.save(newRegion);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedRegion);
    }

    @Override
    public ResponseEntity<Long> deleteRegion(HttpServletRequest request,Long regionid){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id: "+regionid));
        if(!Objects.equals(exsistingRegion.getCreator().getId(), userid)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You can not delete this region");
        }
        regionRepository.deleteById(regionid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionid);
    }
    @Override
    public ResponseEntity<List<Region>> allUserRegions(HttpServletRequest request){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(exsistingUser.getRegions());
    }
}
