package com.example.ExamManagmentSystem.exceptions;

public class TokenNotFoundInCookiesException extends RuntimeException{
    public TokenNotFoundInCookiesException(String msg){
        super(msg);
    }
}
