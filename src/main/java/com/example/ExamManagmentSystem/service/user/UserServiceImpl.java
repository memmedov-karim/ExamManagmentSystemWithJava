package com.example.ExamManagmentSystem.service.user;

import com.example.ExamManagmentSystem.dto.UserRegisterDto;
import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.exceptions.AlreadyExistsException;
import com.example.ExamManagmentSystem.exceptions.NotFoundException;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.validator.DtoValidator;
import com.example.ExamManagmentSystem.validator.UserValidator;
import jakarta.validation.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserValidator userValidator;
    private final DtoValidator dtoValidator;

    @Override
    public User saveUser(@Valid @NotNull UserRegisterDto newUser) {
        dtoValidator.validateDto(newUser);
        if (userRepository.existsUserByEmail(newUser.getEmail())) {
            throw new AlreadyExistsException("User already exists with this email: " + newUser.getEmail());
        }
        User nuser = new User();
        nuser.setName(newUser.getName());
        nuser.setEmail(newUser.getEmail());
        nuser.setPassword(newUser.getPassword());
        User savedUser = userRepository.save(nuser);
        return savedUser;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
    }
    @Override
    public List<User> allUser(){
        return userRepository.findAll();
    }

    @Override
    public Long deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NotFoundException("There is not user with this id: "+id);
        }
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public User updateUser(Long id,User newUser){
        User exsistingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no user with this id: " + id));

//        userValidator.validate(newUser);
        if(!exsistingUser.getName().equals(newUser.getName())){
            if(userRepository.existsUserByName(newUser.getName())){
                throw new AlreadyExistsException("User already exsist with this name: "+newUser.getName());
            }
        }
        exsistingUser.setName((newUser.getName()==null || newUser.getName().isEmpty()) ? exsistingUser.getName():newUser.getName());
        exsistingUser.setPassword((newUser.getPassword()==null || newUser.getPassword().isEmpty()) ? exsistingUser.getPassword():newUser.getPassword());
        User savedUser = userRepository.save(exsistingUser);
        return savedUser;

    }
}
