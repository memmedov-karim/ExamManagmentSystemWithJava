package com.example.ExamManagmentSystem.validator;

import com.example.ExamManagmentSystem.dto.UserRegisterDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Component
public class DtoValidator {
    public <T> void validateDto(T dto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<T> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation failed: " + errorMessages);
        }
    }
}
