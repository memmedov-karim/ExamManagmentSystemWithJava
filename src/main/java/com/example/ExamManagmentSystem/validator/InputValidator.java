package com.example.ExamManagmentSystem.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
@Component
public class InputValidator {
    public boolean checkinputemtynull(Object... inputs){
        for(Object input:inputs){
            if(input==null){
                return false;
            }
            if(input instanceof String){
                if(((String) input).isEmpty()){
                    return false;
                }
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid input for checkinputemtynull method");
            }
        }
        return true;
    }
}
