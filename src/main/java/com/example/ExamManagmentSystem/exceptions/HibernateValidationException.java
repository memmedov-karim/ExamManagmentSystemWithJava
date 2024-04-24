package com.example.ExamManagmentSystem.exceptions;

public class HibernateValidationException extends RuntimeException{
    public HibernateValidationException(String msg){
        super(msg);
    }
}
