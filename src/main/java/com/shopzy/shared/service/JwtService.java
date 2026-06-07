package com.shopzy.shared.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService {


    public String extractUsername(String username) {
        return "";
    }

    public boolean validateToken(String token) {
        return true;
    }
}
