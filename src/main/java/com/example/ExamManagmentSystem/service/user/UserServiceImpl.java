package com.example.ExamManagmentSystem.service.user;

import com.example.ExamManagmentSystem.entity.User;
import com.example.ExamManagmentSystem.repository.UserRepository;
import com.example.ExamManagmentSystem.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public ResponseEntity<User> saveUser(User newUser){
        userValidator.validate(newUser);
        if(userRepository.existsUserByEmail(newUser.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exsist with this email: "+newUser.getEmail());
        }
        User savedUser = userRepository.save(newUser);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(savedUser);
    }

    @Override
    public ResponseEntity<List<User>> allUser(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userRepository.findAll());
    }

    @Override
    public ResponseEntity<Long> deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"There is not user with this id: "+id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(id);
    }

    @Override
    public ResponseEntity<User> updateUser(Long id,User newUser){
        User exsistingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id: " + id));

        userValidator.validate(newUser);
        if(!exsistingUser.getName().equals(newUser.getName())){
            if(userRepository.existsUserByName(newUser.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exsist with this name: "+newUser.getName());
            }
        }
        exsistingUser.setName((newUser.getName()==null || newUser.getName().isEmpty()) ? exsistingUser.getName():newUser.getName());
        exsistingUser.setPassword((newUser.getPassword()==null || newUser.getPassword().isEmpty()) ? exsistingUser.getPassword():newUser.getPassword());
        User savedUser = userRepository.save(exsistingUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedUser);

    }
}
