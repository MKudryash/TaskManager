package com.example.jwttoken.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isAdmin;
}