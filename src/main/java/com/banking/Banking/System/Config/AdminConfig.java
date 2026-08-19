package com.banking.Banking.System.Config;

import com.banking.Banking.System.Model.Role;
import com.banking.Banking.System.Model.Users;
import com.banking.Banking.System.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByEmail("admin@gmail.com")) {

                Users admin = new Users();

                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");

                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );

                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin account created successfully");
            }
        };
    }
}