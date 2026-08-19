package com.banking.Banking.System.Controller;

import com.banking.Banking.System.Config.JwtService;
import com.banking.Banking.System.Service.UserService;
import com.banking.Banking.System.dto.request.LoginRequest;
import com.banking.Banking.System.dto.request.RegisterRequest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid  @RequestBody RegisterRequest request) {

        userService.register(request);

        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Profile accessed successfully");
    }
}