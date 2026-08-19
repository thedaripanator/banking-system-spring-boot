package com.banking.Banking.System.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotBlank(message = "Source account is required")
    String fromAccount;

    @NotBlank(message = "Destination account is required")
    String toAccount;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    BigDecimal amount;
}
