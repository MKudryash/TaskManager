package com.example.jwttoken.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusException extends Throwable {
    public InvalidStatusException(String status)  {
        super("INVALID_Status: " + status);
    }
}
