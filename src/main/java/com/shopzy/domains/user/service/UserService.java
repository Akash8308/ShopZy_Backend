package com.shopzy.domains.user.service;

import com.shopzy.domains.user.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByEmail(String email);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
