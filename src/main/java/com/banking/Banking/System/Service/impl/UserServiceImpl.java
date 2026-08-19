package com.banking.Banking.System.Service.impl;

import com.banking.Banking.System.Exception.EmailAlreadyExistsException;
import com.banking.Banking.System.Model.Role;
import com.banking.Banking.System.Model.Users;
import com.banking.Banking.System.Repository.UserRepository;
import com.banking.Banking.System.Service.UserService;
import com.banking.Banking.System.dto.request.RegisterRequest;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

    }
}
