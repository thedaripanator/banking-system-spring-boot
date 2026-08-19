
package com.banking.Banking.System.dto.request;

import com.banking.Banking.System.Model.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BankAccountResponse {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}