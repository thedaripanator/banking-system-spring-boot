package com.banking.Banking.System.dto.response;

import com.banking.Banking.System.Model.TransactionStatus;
import com.banking.Banking.System.Model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private Long id;

    private String transactionReference;

    private TransactionType type;

    private BigDecimal amount;

    private String fromAccount;

    private String toAccount;

    private TransactionStatus status;

    private LocalDateTime createdAt;
}