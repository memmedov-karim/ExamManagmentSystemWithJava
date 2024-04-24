package com.example.ExamManagmentSystem.exceptions;

public class FileGenerationException extends RuntimeException{
    public FileGenerationException(String msg,Throwable c){
        super(msg,c);
    }
}
