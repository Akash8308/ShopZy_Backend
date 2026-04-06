package com.shopzy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/")  // maps root URL
    public String home() {
        return "Welcome to ShopZy!";
    }
}