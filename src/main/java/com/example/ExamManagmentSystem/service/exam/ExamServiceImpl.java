package com.example.ExamManagmentSystem.service.exam;
import com.example.ExamManagmentSystem.entity.Region;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.entity.Exam;
import com.example.ExamManagmentSystem.repository.ExamRepository;
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

@Service
public class ExamServiceImpl implements ExamService{
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final RegionRepository regionRepository;
    private final InputValidator inputValidator;
    private final JsonWebTokenService jsonWebTokenService;
    public ExamServiceImpl(UserRepository userRepository, ExamRepository examRepository, RegionRepository regionRepository, InputValidator inputValidator, JsonWebTokenService jsonWebTokenService){
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.regionRepository = regionRepository;
        this.inputValidator = inputValidator;
        this.jsonWebTokenService = jsonWebTokenService;
    }
    @Override
    public ResponseEntity<Exam> saveExam(HttpServletRequest request, Exam newExam){
        Long userid = jsonWebTokenService.getUserIdFromToken(request,"tokenU");
        User exsistingUser = userRepository.findById(userid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not user with this id: "+userid));
        if(!inputValidator.checkinputemtynull(newExam.getName(),newExam.getTitle())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name,title is required");
        }
        if(examRepository.existsByNameAndCreatorId(newExam.getName(),userid)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This name: "+newExam.getName()+" already used");
        }
        newExam.setCreator(exsistingUser);
        Exam savedExam = examRepository.save(newExam);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedExam);

    }
    @Override
    public ResponseEntity<List<Exam>> allRegionExams(HttpServletRequest request){
        Long regionid = jsonWebTokenService.getUserIdFromToken(request,"tokenR");
        Region exsistingRegion = regionRepository.findById(regionid)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"There is not region with this id: "+regionid));
        List<Exam> regionsexams = exsistingRegion.getCreator().getExams();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(regionsexams);
    }
}
