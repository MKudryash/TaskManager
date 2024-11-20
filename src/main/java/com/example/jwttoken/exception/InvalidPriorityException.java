package com.example.jwttoken.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPriorityException extends Throwable {
    public InvalidPriorityException(String status)  {
        super("INVALID_Priority: " + status);
    }
}