package com.example.jwttoken.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    private String login;
    private String password;
}