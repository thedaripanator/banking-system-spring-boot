package com.banking.Banking.System.Service.impl;


import com.banking.Banking.System.Exception.AccountNotFoundException;
import com.banking.Banking.System.Exception.InvalidTransactionException;
import com.banking.Banking.System.Model.BankAccount;
import com.banking.Banking.System.Model.TransactionType;
import com.banking.Banking.System.Repository.BankAccountRepository;
import com.banking.Banking.System.Repository.UserRepository;
import com.banking.Banking.System.Service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    void shouldTransferMoneySuccessfully() {

        // Arrange
        String senderAccountNumber = "SENDER123";
        String receiverAccountNumber = "RECEIVER456";
        String email = "user@gmail.com";

        BigDecimal transferAmount = new BigDecimal("500");

        BankAccount sender = new BankAccount();
        sender.setAccountNumber(senderAccountNumber);
        sender.setBalance(new BigDecimal("10000"));

        BankAccount receiver = new BankAccount();
        receiver.setAccountNumber(receiverAccountNumber);
        receiver.setBalance(new BigDecimal("2000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        senderAccountNumber,
                        email))
                .thenReturn(Optional.of(sender));

        when(bankAccountRepository
                .findByAccountNumber(receiverAccountNumber))
                .thenReturn(Optional.of(receiver));

        // Act
        bankAccountService.transfer(
                senderAccountNumber,
                receiverAccountNumber,
                email,
                transferAmount
        );

        // Assert
        assertEquals(
                new BigDecimal("9500"),
                sender.getBalance()
        );

        assertEquals(
                new BigDecimal("2500"),
                receiver.getBalance()
        );

        verify(bankAccountRepository).save(sender);
        verify(bankAccountRepository).save(receiver);

        verify(transactionService).createTransaction(
                TransactionType.TRANSFER,
                transferAmount,
                senderAccountNumber,
                receiverAccountNumber
        );
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        // Arrange
        String senderAccountNumber = "SENDER123";
        String receiverAccountNumber = "RECEIVER456";
        String email = "user@gmail.com";

        BigDecimal transferAmount = new BigDecimal("5000");

        BankAccount sender = new BankAccount();
        sender.setAccountNumber(senderAccountNumber);
        sender.setBalance(new BigDecimal("1000"));

        BankAccount receiver = new BankAccount();
        receiver.setAccountNumber(receiverAccountNumber);
        receiver.setBalance(new BigDecimal("2000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        senderAccountNumber,
                        email))
                .thenReturn(Optional.of(sender));

        when(bankAccountRepository
                .findByAccountNumber(receiverAccountNumber))
                .thenReturn(Optional.of(receiver));

        // Act + Assert
        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.transfer(
                        senderAccountNumber,
                        receiverAccountNumber,
                        email,
                        transferAmount
                )
        );

        // Verify that money was NOT saved
        verify(bankAccountRepository, never()).save(sender);
        verify(bankAccountRepository, never()).save(receiver);

        // Verify that transaction was NOT created
        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenAmountIsNull() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.transfer(
                        "SENDER123",
                        "RECEIVER456",
                        "user@gmail.com",
                        null
                )
        );

        verify(bankAccountRepository, never()).save(any());
        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {

        BigDecimal amount = new BigDecimal("-500");

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.transfer(
                        "SENDER123",
                        "RECEIVER456",
                        "user@gmail.com",
                        amount
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        BigDecimal amount = BigDecimal.ZERO;

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.transfer(
                        "SENDER123",
                        "RECEIVER456",
                        "user@gmail.com",
                        amount
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }

    @Test
    void shouldThrowExceptionWhenTransferringToSameAccount() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.transfer(
                        "ACCOUNT123",
                        "ACCOUNT123",
                        "user@gmail.com",
                        new BigDecimal("500")
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenSenderAccountDoesNotExist() {

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        "SENDER123",
                        "user@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> bankAccountService.transfer(
                        "SENDER123",
                        "RECEIVER456",
                        "user@gmail.com",
                        new BigDecimal("500")
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }

    @Test
    void shouldThrowExceptionWhenReceiverAccountDoesNotExist() {

        BankAccount sender = new BankAccount();
        sender.setAccountNumber("SENDER123");
        sender.setBalance(new BigDecimal("10000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        "SENDER123",
                        "user@gmail.com"))
                .thenReturn(Optional.of(sender));

        when(bankAccountRepository
                .findByAccountNumber("RECEIVER456"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> bankAccountService.transfer(
                        "SENDER123",
                        "RECEIVER456",
                        "user@gmail.com",
                        new BigDecimal("500")
                )
        );

        // Sender should not be saved
        verify(bankAccountRepository, never()).save(any());

        // No transaction should be created
        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldDepositMoneySuccessfully() {

        String accountNumber = "ACCOUNT123";
        String email = "user@gmail.com";
        BigDecimal depositAmount = new BigDecimal("1000");

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("5000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        accountNumber,
                        email))
                .thenReturn(Optional.of(account));

        bankAccountService.deposit(
                accountNumber,
                email,
                depositAmount
        );

        assertEquals(
                new BigDecimal("6000"),
                account.getBalance()
        );

        verify(bankAccountRepository).save(account);

        verify(transactionService).createTransaction(
                TransactionType.DEPOSIT,
                depositAmount,
                null,
                accountNumber
        );
    }
    @Test
    void shouldThrowExceptionWhenDepositAmountIsNull() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.deposit(
                        "ACCOUNT123",
                        "user@gmail.com",
                        null
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenDepositAmountIsZero() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.deposit(
                        "ACCOUNT123",
                        "user@gmail.com",
                        BigDecimal.ZERO
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldWithdrawMoneySuccessfully() {

        String accountNumber = "ACCOUNT123";
        String email = "user@gmail.com";
        BigDecimal withdrawAmount = new BigDecimal("1000");

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("5000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        accountNumber,
                        email))
                .thenReturn(Optional.of(account));

        BigDecimal result = bankAccountService.withdraw(
                accountNumber,
                email,
                withdrawAmount
        );

        assertEquals(
                new BigDecimal("4000"),
                account.getBalance()
        );

        assertEquals(
                new BigDecimal("4000"),
                result
        );

        verify(bankAccountRepository).save(account);

        verify(transactionService).createTransaction(
                TransactionType.WITHDRAW,
                withdrawAmount,
                accountNumber,
                null
        );
    }
    @Test
    void shouldThrowExceptionWhenWithdrawAmountExceedsBalance() {

        String accountNumber = "ACCOUNT123";
        String email = "user@gmail.com";
        BigDecimal withdrawAmount = new BigDecimal("6000");

        BankAccount account = new BankAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(new BigDecimal("5000"));

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        accountNumber,
                        email))
                .thenReturn(Optional.of(account));

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.withdraw(
                        accountNumber,
                        email,
                        withdrawAmount
                )
        );

        assertEquals(
                new BigDecimal("5000"),
                account.getBalance()
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenWithdrawAmountIsNull() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.withdraw(
                        "ACCOUNT123",
                        "user@gmail.com",
                        null
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenWithdrawAmountIsZero() {

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.withdraw(
                        "ACCOUNT123",
                        "user@gmail.com",
                        BigDecimal.ZERO
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenWithdrawAmountIsNegative() {

        BigDecimal amount = new BigDecimal("-500");

        assertThrows(
                InvalidTransactionException.class,
                () -> bankAccountService.withdraw(
                        "ACCOUNT123",
                        "user@gmail.com",
                        amount
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
    @Test
    void shouldThrowExceptionWhenWithdrawAccountDoesNotExist() {

        when(bankAccountRepository
                .findByAccountNumberAndUserEmail(
                        "ACCOUNT123",
                        "user@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> bankAccountService.withdraw(
                        "ACCOUNT123",
                        "user@gmail.com",
                        new BigDecimal("500")
                )
        );

        verify(bankAccountRepository, never()).save(any());

        verify(transactionService, never()).createTransaction(
                any(),
                any(),
                any(),
                any()
        );
    }
}