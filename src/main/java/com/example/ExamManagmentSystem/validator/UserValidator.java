package com.example.ExamManagmentSystem.validator;

import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
@Component
public class UserValidator {
    public void validate(User user){
        if(user.getName()==null || user.getName().isEmpty() || user.getPassword()==null || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"name,role and password is required.");
        }
        if(user.getPassword().length()<6){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"password length must be min 6 characters");
        }

//        if(userRepository.existsUserByName(user.getName())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exsist with this name: "+user.getName());
//        }
    }


}
