package com.shopzy.domains.auth.controller;

import com.shopzy.domains.auth.dto.AuthResponse;
import com.shopzy.domains.auth.dto.LoginRequest;
import com.shopzy.domains.auth.dto.RegisterRequest;
import com.shopzy.domains.auth.service.AuthService;
import com.shopzy.domains.user.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return ResponseEntity.ok(
                authService.verify(user)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}