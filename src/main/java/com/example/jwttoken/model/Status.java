package com.example.jwttoken.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Status implements GrantedAuthority {

    PENDING("pending"),
    COMPLETED("completed"),
    INPROGRESS("in progress");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}