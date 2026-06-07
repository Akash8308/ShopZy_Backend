package com.shopzy.controller;

import com.shopzy.domains.user.model.Users;
import com.shopzy.shared.dto.AuthResponse;
import com.shopzy.shared.service.AuthService;
import com.shopzy.shared.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String home() {
        return "Welcome to ShopZy!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Users user) {
        return authService.verify(user);
    }
}