package com.shopzy.domains.auth.dto;

public record RegisterRequest(
        String name,
        String email,
        String password
) {}
