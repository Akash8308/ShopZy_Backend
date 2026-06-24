package com.shopzy.domains.auth.service;

import com.shopzy.domains.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository  userRepository;

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Email not found")
                );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Email not found")
                );
    }
}
