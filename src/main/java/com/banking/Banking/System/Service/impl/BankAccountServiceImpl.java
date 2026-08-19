package com.banking.Banking.System.Service.impl;

import com.banking.Banking.System.Exception.AccountNotFoundException;
import com.banking.Banking.System.Exception.InvalidTransactionException;
import com.banking.Banking.System.Model.BankAccount;
import com.banking.Banking.System.Model.TransactionType;
import com.banking.Banking.System.Model.Users;
import com.banking.Banking.System.Repository.BankAccountRepository;
import com.banking.Banking.System.Repository.UserRepository;
import com.banking.Banking.System.Service.BankAccountService;
import com.banking.Banking.System.Service.TransactionService;
import com.banking.Banking.System.dto.request.BankAccountResponse;
import com.banking.Banking.System.dto.request.CreateAccountRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.*;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public BankAccountServiceImpl(
            BankAccountRepository bankAccountRepository,
            UserRepository userRepository,
            TransactionService transactionService) {

        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }


    @Override
    public void CreateAccount(
            CreateAccountRequest request,
            String email) {

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        BankAccount account = new BankAccount();
        account.setAccountNumber(
                generateAccountNumber()
        );
        account.setAccountType(
                request.getAccountType()
        );
        account.setBalance(
                BigDecimal.ZERO
        );
        account.setUser(user);
        bankAccountRepository.save(account);
    }

    private String generateAccountNumber() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);
    }
    @Override
    public List<BankAccountResponse> getAccounts(String email) {
        List<BankAccount> accounts =
                bankAccountRepository.findByUserEmail(email);
        return accounts.stream()
                .map(this::convertToResponse)
                .toList();
    }

    private BankAccountResponse convertToResponse(
            BankAccount account) {
        BankAccountResponse response =
                new BankAccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(
                account.getAccountNumber()
        );
        response.setAccountType(
                account.getAccountType()
        );
        response.setBalance(
                account.getBalance()
        );
        response.setCreatedAt(
                account.getCreatedAt()
        );
        return response;
    }


    @Override

    public BankAccountResponse getAccount(
            String accountNumber,
            String email
    ) {
        BankAccount account = bankAccountRepository.findByAccountNumberAndUserEmail(accountNumber, email).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        return convertToResponse(account);
    }

    @Override
    public void deposit(String accountNumber, String email, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be greater than Zero");
        }

        BankAccount account = bankAccountRepository.findByAccountNumberAndUserEmail(accountNumber, email).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        account.setBalance(account.getBalance().add(amount));
        bankAccountRepository.save(account);
        transactionService.createTransaction(TransactionType.DEPOSIT, amount, null, accountNumber);
    }

    @Override
    public BigDecimal withdraw(String accountNumber, String email, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be greater than Zero");
        }
        BankAccount account = bankAccountRepository.findByAccountNumberAndUserEmail(accountNumber, email).orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InvalidTransactionException("Insufficient Balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        bankAccountRepository.save(account);
        transactionService.createTransaction(
                TransactionType.WITHDRAW,
                amount,
                accountNumber,
                null
        );
        return account.getBalance();
    }

    @Override
    @Transactional
    public void transfer(String fromAccount, String toAccount, String email, BigDecimal amount) {
        // Validate the amount
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <=0) {
            throw new InvalidTransactionException("Amount must be greater than zero");
        }
        // 2.Preventing Transferring to the same Amount
        if (fromAccount.equals(toAccount)) {
            throw new InvalidTransactionException("Cannot Transfer to the same Account");
        }
        BankAccount sender = bankAccountRepository.findByAccountNumberAndUserEmail(fromAccount, email).orElseThrow(() -> new AccountNotFoundException("sender Account  not found"));
        BankAccount receiver = bankAccountRepository.findByAccountNumber(toAccount).orElseThrow(() -> new AccountNotFoundException("Receiver Account not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InvalidTransactionException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        bankAccountRepository.save(sender);
        bankAccountRepository.save(receiver);
        transactionService.createTransaction(
                TransactionType.TRANSFER,
                amount,
                fromAccount,
                toAccount
        );
    }


}
