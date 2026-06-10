package com.shopzy.domains.auth.dto;

public record RegisterRequest(
        String username,
        String email,
        String password
) {}
