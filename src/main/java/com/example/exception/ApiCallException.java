package com.example.exception;

public class ApiCallException extends RuntimeException{
    public ApiCallException(String message) {
        super(message);
    }
}
