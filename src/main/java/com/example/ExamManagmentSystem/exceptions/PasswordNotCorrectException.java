package com.example.ExamManagmentSystem.exceptions;

public class PasswordNotCorrectException extends RuntimeException{
    public PasswordNotCorrectException(String msg){
        super(msg);
    }
}
