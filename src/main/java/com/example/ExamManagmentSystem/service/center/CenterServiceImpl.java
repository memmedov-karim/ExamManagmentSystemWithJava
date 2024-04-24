package com.example.ExamManagmentSystem.service.center;

import com.example.ExamManagmentSystem.dto.NewCenterDto;
import com.example.ExamManagmentSystem.entity.Center;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.exceptions.AlreadyExistsException;
import com.example.ExamManagmentSystem.exceptions.NotFoundException;
import com.example.ExamManagmentSystem.exceptions.ProcessNotAllowedException;
import com.example.ExamManagmentSystem.repository.CenterRepository;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.validator.DtoValidator;
import com.example.ExamManagmentSystem.validator.InputValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;
    private final RegionRepository regionRepository;
    private final InputValidator inputValidator;
    private final JsonWebTokenService jsonWebTokenService;
    private final DtoValidator dtoValidator;
    @Override
    public Center saveCenter(HttpServletRequest request,@Valid @NotNull NewCenterDto newCenter){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        dtoValidator.validateDto(newCenter);
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new NotFoundException("There is not region with this id: "+regionid));
//        System.out.println(newCenter.getName()+":"+newCenter.getCity()+":"+newCenter.getCode());
        if(centerRepository.existsByNameAndRegionId(newCenter.getName(), regionid)){
            throw new AlreadyExistsException("This name: "+newCenter.getName()+" already exist");
        }
        Center newCenterInstance = new Center();
        newCenterInstance.setRegion(exsistingRegion);
        newCenterInstance.setName(newCenter.getName());
        newCenterInstance.setCity(newCenter.getCity());
        newCenterInstance.setCode(newCenter.getCode());
        return centerRepository.save(newCenterInstance);
    }

    @Override
    public Long deleteCenter(HttpServletRequest request,Long centerId){
        Long regionId = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionId)
                .orElseThrow(()->new NotFoundException("There is not region with this id:"+regionId));
        Center exsistingCenter = centerRepository.findById(centerId)
                .orElseThrow(()->new NotFoundException("There is not center with this id:"+centerId));
        if(!Objects.equals(exsistingCenter.getRegion().getId(), regionId)){
            throw new ProcessNotAllowedException("You can not delete this center");
        }
        centerRepository.deleteById(centerId);
        return exsistingCenter.getId();
    }
}
