package com.shopzy.domains.user.service;

import com.shopzy.domains.user.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    Users createUser(Users user);
    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users getUserByEmail(String email);
    Users updateUser(Long id, Users user);
}
