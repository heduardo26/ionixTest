package com.example.exception;

public class DataDuplicateException extends RuntimeException{
    public DataDuplicateException(String message){
        super(message);
    }
}
