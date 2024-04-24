package com.example.ExamManagmentSystem.exceptions;

import com.example.ExamManagmentSystem.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException ex, WebRequest request) {
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Resource not found: " + ex.getMessage());
        errorDto.setStatus(HttpStatus.NOT_FOUND.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(
            AlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Resource already exists: " + ex.getMessage());
        errorDto.setStatus(HttpStatus.CONFLICT.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
    }
    @ExceptionHandler(HibernateValidationException.class)
    public ResponseEntity<ErrorResponse> handleHibernateValidationException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Hibernate validation error" + ex.getMessage());
        errorDto.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }
    @ExceptionHandler(ProcessNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleProcessNotAllowedException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Process not allowed error" + ex.getMessage());
        errorDto.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDto);
    }
    @ExceptionHandler(FileGenerationException.class)
    public ResponseEntity<ErrorResponse> handleFileGenerationException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Failed to generate file" + ex.getMessage());
        errorDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }
    @ExceptionHandler(PasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> handlePasswordNotCorrectException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Incorrect password" + ex.getMessage());
        errorDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }
    @ExceptionHandler(TokenNotFoundInCookiesException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundInCookiesException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Token not found in cookies" + ex.getMessage());
        errorDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }
    @ExceptionHandler(CookiesNotFoundInRequestException.class)
    public ResponseEntity<ErrorResponse> handleCookiesNotFoundInRequestException(Exception ex,WebRequest request){
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Cookies not found in request" + ex.getMessage());
        errorDto.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorDto = new ErrorResponse();
        errorDto.setMessage("Internal server error: " + ex.getMessage());
        errorDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }


}
