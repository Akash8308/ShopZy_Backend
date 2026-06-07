package com.shopzy.domains.user.service;

import com.shopzy.domains.user.model.Users;

import java.util.List;

public interface UserService {
    Users createUser(Users user);
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByEmail(String email);
    Users updateUser(Long id, Users user);
    void deleteUser(Long id);
}
