package com.banking.Banking.System.Controller;

import com.banking.Banking.System.Service.BankAccountService;
import com.banking.Banking.System.dto.request.AmountRequest;
import com.banking.Banking.System.dto.request.BankAccountResponse;
import com.banking.Banking.System.dto.request.CreateAccountRequest;
import com.banking.Banking.System.dto.request.TransferRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(
            BankAccountService bankAccountService) {

        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(

            @Valid @RequestBody CreateAccountRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        bankAccountService.CreateAccount(
                request,
                email
        );

        return ResponseEntity.ok(
                "Bank account created successfully"
        );
    }

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAccounts(
            Authentication authentication) {

        String email = authentication.getName();

        List<BankAccountResponse> accounts =
                bankAccountService.getAccounts(email);

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccount(@PathVariable String accountNumber, Authentication authentication) {
        String email = authentication.getName();
        BankAccountResponse account = bankAccountService.getAccount(accountNumber, email);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(
            @Valid @PathVariable String accountNumber,
            @RequestBody AmountRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        bankAccountService.deposit(
                accountNumber,
                email,
                request.getAmount()
        );

        return ResponseEntity.ok("Amount deposited successfully");
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody AmountRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        BigDecimal remaining = bankAccountService.withdraw(accountNumber, email, request.getAmount());

        return ResponseEntity.ok("Amount Withdrawn Successfully \n Remaining Balance:" + remaining);

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest transferRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        bankAccountService.transfer(transferRequest.getFromAccount(),
                transferRequest.getToAccount(),
                email,
                transferRequest.getAmount()
        );

        return ResponseEntity.ok("Transferred Money Successfully");

    }

}