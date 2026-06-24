package com.shopzy.domains.auth.controller;

import com.shopzy.domains.auth.dto.*;
import com.shopzy.domains.auth.service.AuthService;
import com.shopzy.domains.user.model.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        log.info("Login request received for email: {}", request.getEmail());

        return ResponseEntity.ok(
                authService.verify(
                         Users.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        RegisterResponse registerResponse = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/exchange")
    public AuthResponse exchangeCode(@RequestParam String code) {
        return authService.exchange(code);
    }
}