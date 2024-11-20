package com.example.jwttoken.controller;

import com.example.jwttoken.exception.ApiError;
import com.example.jwttoken.model.User;
import com.example.jwttoken.request.AuthRequest;
import com.example.jwttoken.service.AuthService;
import com.example.jwttoken.request.JwtRequest;
import com.example.jwttoken.response.JwtResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("SignIn")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws AuthException {
        try {
            final JwtResponse token = authService.login(authRequest);
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("SignUp")
    public ResponseEntity<?> signup(@RequestBody JwtRequest authRequest) throws AuthException {
        try {
            authService.signup(authRequest);
            return ResponseEntity.ok("Пользователь успешно зарегистрирован");
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
        }
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleAuthException(AuthException ex) {
        return ResponseEntity.badRequest().body(new ApiError(ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleExpiredJwtException(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Token expired"));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Unsupported jwt"));
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Malformed jwt"));
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleSignatureException(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiError("Invalid signature"));
    }

}
