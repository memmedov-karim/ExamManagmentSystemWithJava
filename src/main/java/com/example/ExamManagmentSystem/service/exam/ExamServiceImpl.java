package com.example.ExamManagmentSystem.service.exam;
import com.example.ExamManagmentSystem.dto.NewExamDto;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.repository.ExamRepository;
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

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService{
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final RegionRepository regionRepository;
    private final InputValidator inputValidator;
    private final JsonWebTokenService jsonWebTokenService;
    private final DtoValidator dtoValidator;
    @Override
    public Exam saveExam(HttpServletRequest request,@Valid @NotNull NewExamDto newExam){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        dtoValidator.validateDto(newExam);
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        if(examRepository.existsByNameAndCreatorId(newExam.getName(),userid)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This name: "+newExam.getName()+" already used");
        }
        Exam newExamInstance = new Exam();
        newExamInstance.setCreator(exsistingUser);
        newExamInstance.setName(newExam.getName());
        newExamInstance.setTitle(newExam.getTitle());
        newExamInstance.setDateandtime(newExam.getDateandtime());
        return examRepository.save(newExamInstance);

    }
    @Override
    public List<Exam> allRegionExams(HttpServletRequest request){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id: "+regionid));
        List<Exam> regionsexams = exsistingRegion.getCreator().getExams();
        return regionsexams;
    }
}
