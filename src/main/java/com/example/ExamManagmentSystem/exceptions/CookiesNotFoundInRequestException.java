package com.example.ExamManagmentSystem.exceptions;

public class CookiesNotFoundInRequestException extends RuntimeException{
    public CookiesNotFoundInRequestException(String msg){
        super(msg);
    }
}
