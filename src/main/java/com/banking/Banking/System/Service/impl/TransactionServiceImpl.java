package com.banking.Banking.System.Service.impl;


import com.banking.Banking.System.Exception.AccountNotFoundException;
import com.banking.Banking.System.Model.BankAccount;
import com.banking.Banking.System.Model.Transaction;
import com.banking.Banking.System.Model.TransactionStatus;
import com.banking.Banking.System.Model.TransactionType;
import com.banking.Banking.System.Repository.BankAccountRepository;
import com.banking.Banking.System.Repository.TransactionRepository;
import com.banking.Banking.System.Service.TransactionService;
import com.banking.Banking.System.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    TransactionServiceImpl(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<TransactionResponse> getMyTransactions(
            String email) {
        List<BankAccount> accounts = bankAccountRepository.findByUserEmail(email);
        List<TransactionResponse> result = new ArrayList<>();
        for (BankAccount account : accounts) {
            List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccount(account.getAccountNumber(), account.getAccountNumber());
            for (Transaction transaction : transactions) {
                result.add(convertToResponse(transaction));
            }
        }
        return result;
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setTransactionReference(transaction.getTransactionReference());
        response.setAmount(transaction.getAmount());
        response.setStatus(transaction.getStatus());
        response.setFromAccount(transaction.getFromAccount());
        response.setToAccount(transaction.getToAccount());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }

    @Override
    public Page<TransactionResponse> getAccountTransactions(
            String accountNumber,
            String email,
            TransactionType type,
            Pageable pageable) {

        bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        accountNumber,
                        email
                )
                .orElseThrow(() ->
                        new AccountNotFoundException("Account Not Found"));

        Page<Transaction> transactions;

        if (type == null) {

            transactions =
                    transactionRepository.findByFromAccountOrToAccount(
                            accountNumber,
                            accountNumber,
                            pageable
                    );

        } else {

            transactions =
                    transactionRepository.findByAccountAndType(
                            accountNumber,
                            type,
                            pageable
                    );
        }

        return transactions.map(this::convertToResponse);
    }

    @Override
    public void createTransaction(TransactionType type, BigDecimal amount, String fromAccount, String toAccount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionReference(
                "TXN-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);
    }
}
