package com.example.ExamManagmentSystem.service.auth.regionauth;

import com.example.ExamManagmentSystem.dto.RegionLoginDto;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.service.region.RegionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
@Service
public class RegionAuthServiceImpl implements RegionAuthService {
    private final RegionRepository regionRepository;
    private final JsonWebTokenService jsonWebTokenService;
    public RegionAuthServiceImpl(RegionRepository regionRepository, JsonWebTokenService jsonWebTokenService) {
        this.regionRepository = regionRepository;
        this.jsonWebTokenService = jsonWebTokenService;
    }

    @Override
    public ResponseEntity<String> loginRegion(RegionLoginDto regionLoginDto, HttpServletResponse response){
        String username = regionLoginDto.getUsername();
        String password = regionLoginDto.getPassword();
        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"username,password is required");
        }
        Region exsistingRegion = regionRepository.findByUsername(username);
        if(exsistingRegion == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this username:"+username);

        }
        if(!Objects.equals(exsistingRegion.getPassword(), password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password is not correct");
        }

        HttpHeaders headers = jsonWebTokenService.sendTokenWithCookie(exsistingRegion.getId(),"tokenR",response);

        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).body("Succesfull login");

    }

    @Override
    public ResponseEntity<String> logoutRegion(HttpServletRequest request,HttpServletResponse response){
        return jsonWebTokenService.clearCookie(request,response,"tokenR");
    }
}
