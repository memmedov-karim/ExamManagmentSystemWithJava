package com.example.ExamManagmentSystem.dto;

public class ErrorResponse {
    private int statuscode;
    private String errormessage;
    public ErrorResponse(int statuscode,String errormessage){
        this.statuscode = statuscode;
        this.errormessage = errormessage;
    }
    public int getStatuscode(){
        return this.statuscode;
    }
    public void setStatuscode(int statuscode){
        this.statuscode = statuscode;
    }
    public String getErrormessage(){
        return this.errormessage;
    }
    public void setErrormessage(String errormessage){
        this.errormessage = errormessage;
    }
}
