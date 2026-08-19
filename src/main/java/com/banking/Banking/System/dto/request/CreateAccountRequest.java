package com.banking.Banking.System.dto.request;

import com.banking.Banking.System.Model.AccountType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountRequest {

    @NotNull(message = "Account type is required")
    private AccountType accountType;
}
