package com.shopzy.service;

import com.shopzy.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    UserDetails loadUserByUsername(String name);
}