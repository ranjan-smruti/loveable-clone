package com.project.loveable_clone.ExceptionHandler;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
