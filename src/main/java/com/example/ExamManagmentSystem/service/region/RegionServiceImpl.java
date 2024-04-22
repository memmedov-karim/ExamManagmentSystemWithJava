package com.example.ExamManagmentSystem.service.region;

import com.example.ExamManagmentSystem.dto.NewRegionDto;
import com.example.ExamManagmentSystem.dto.NewTicketDto;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.repository.RegionRepository;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.service.auth.jwt.JsonWebTokenService;
import com.example.ExamManagmentSystem.validator.DtoValidator;
import com.example.ExamManagmentSystem.validator.InputValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final UserRepository userRepository;
    private final JsonWebTokenService jsonWebTokenService;
    private final InputValidator inputValidator;
    private final RegionRepository regionRepository;
    private final DtoValidator dtoValidator;
    @Override
    public Region saveRegion(HttpServletRequest request, @Valid @NotNull NewRegionDto newRegion){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        dtoValidator.validateDto(newRegion);
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id: " + userid));
        if(regionRepository.existsByNameAndCreatorId(newRegion.getName(), userid)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This name: "+newRegion.getName()+" already used");
        }
        Region newRegionInstance = new Region();
        newRegionInstance.setCreator(exsistingUser);
        newRegionInstance.setName(newRegion.getName());
        newRegionInstance.setUsername(newRegion.getUsername());
        return regionRepository.save(newRegionInstance);
    }

    @Override
    public Long deleteRegion(HttpServletRequest request,Long regionid){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id: "+regionid));
        if(!Objects.equals(exsistingRegion.getCreator().getId(), userid)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You can not delete this region");
        }
        regionRepository.deleteById(regionid);
        return regionid;
    }
    @Override
    public List<Region> allUserRegions(HttpServletRequest request){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        return exsistingUser.getRegions();
    }
}
