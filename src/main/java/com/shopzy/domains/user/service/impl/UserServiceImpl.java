package com.shopzy.domains.user.service.impl;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.domains.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Users updateUser(Long id, Users user) {
        Users existing = userRepository.findById(id).orElseThrow();
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
