package com.shopzy.controller;

import com.shopzy.domains.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/home")  // maps root URL
    public String home() {
        return "Welcome to ShopZy!";
    }

    @PostMapping
    public String login(@RequestBody User user) {

    }
}