package com.shopzy.domains.user.service.impl;

import com.shopzy.domains.user.model.Users;
import com.shopzy.domains.user.repository.UserRepository;
import com.shopzy.domains.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Users updateUser(Long id, Users user) {
        Users existing = userRepository.findById(id).orElseThrow();
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);
    }


}
