package com.example.jwttoken.service;

import com.example.jwttoken.model.Role;
import com.example.jwttoken.request.AuthRequest;
import com.example.jwttoken.request.JwtRequest;
import com.example.jwttoken.response.JwtResponse;
import com.example.jwttoken.model.User;
import com.example.jwttoken.security.JwtAuthentication;
import com.example.jwttoken.security.JwtProvider;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(@NonNull AuthRequest authRequest) throws AuthException {
        final User user = userService.getUserByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            return new JwtResponse(accessToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public void signup(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getUserByLogin(authRequest.getLogin()).orElse(null);
        if (user != null) {
            throw new AuthException("Пользователь с таким логином уже существует");
        }

        final User newUser = new User();
        newUser.setLogin(authRequest.getLogin());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        newUser.setFirstName(authRequest.getFirstName());
        newUser.setLastName(authRequest.getLastName());
        if (authRequest.getIsAdmin())
            newUser.setRole(Collections.singleton(Role.ADMIN));
        else
            newUser.setRole(Collections.singleton(Role.USER));
        userService.createUser(newUser);
    }

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}