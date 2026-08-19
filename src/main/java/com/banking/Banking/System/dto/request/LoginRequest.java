package com.banking.Banking.System.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
