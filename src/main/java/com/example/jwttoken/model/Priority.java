package com.example.jwttoken.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@RequiredArgsConstructor
public enum Priority implements GrantedAuthority {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}
