package com.banking.Banking.System.Controller;

import com.banking.Banking.System.Model.TransactionType;
import com.banking.Banking.System.Service.TransactionService;
import com.banking.Banking.System.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getMyTransaction(Authentication authentication) {
        String email = authentication.getName();
        List<TransactionResponse> transactions = transactionService.getMyTransactions(email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Page<TransactionResponse>> getAccountTransactions(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TransactionType type,
            Authentication authentication) {

        String email = authentication.getName();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<TransactionResponse> transactions =
                transactionService.getAccountTransactions(
                        accountNumber,
                        email,
                        type,
                        pageable
                );

        return ResponseEntity.ok(transactions);
    }
}
