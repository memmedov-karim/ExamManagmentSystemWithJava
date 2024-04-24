package com.example.ExamManagmentSystem.exceptions;

public class ProcessNotAllowedException extends RuntimeException{
    public ProcessNotAllowedException(String msg){
        super(msg);
    }
}
