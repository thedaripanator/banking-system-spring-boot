package com.banking.Banking.System.Service;

import com.banking.Banking.System.Model.Transaction;
import com.banking.Banking.System.Model.TransactionType;
import com.banking.Banking.System.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<TransactionResponse> getMyTransactions(String email);

    Page<TransactionResponse> getAccountTransactions(
            String accountNumber,
            String email,
            TransactionType type,
            Pageable pageable
    );
    void createTransaction(
            TransactionType type,
            BigDecimal amount,
            String fromAccount,
            String toAccount
    );
}
