package com.shopzy.domains.user.controller;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return userServiceImpl.createUser(user);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id);
    }

    @GetMapping("/email")
    public Users getUserByEmail(@RequestParam String email) {
        return userServiceImpl.getUserByEmail(email);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Long id, @RequestBody Users user) {
        return userServiceImpl.updateUser(id, user);
    }
}
