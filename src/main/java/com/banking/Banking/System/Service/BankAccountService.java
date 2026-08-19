package com.banking.Banking.System.Service;


import com.banking.Banking.System.Model.BankAccount;
import com.banking.Banking.System.dto.request.BankAccountResponse;
import com.banking.Banking.System.dto.request.CreateAccountRequest;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {
    void CreateAccount(CreateAccountRequest createAccountRequest, String email);

    List<BankAccountResponse> getAccounts(String email);

    BankAccountResponse getAccount(
            String accountNumber,
            String email
    );

    void deposit(
            String accountNumber,
            String email,
            BigDecimal amount

    );

    BigDecimal withdraw(
            String accountNumber,
            String email,
            BigDecimal amount

    );

    void transfer(
            String fromAccount,
            String toAccount,
            String email,
            BigDecimal amount
    );


}
