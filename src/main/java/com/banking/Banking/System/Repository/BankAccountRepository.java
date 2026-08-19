package com.banking.Banking.System.Repository;


import com.banking.Banking.System.Model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);

    List<BankAccount> findByUserId(Long userId);
    List<BankAccount> findByUserEmail(String email);
    Optional<BankAccount> findByAccountNumberAndUserEmail(
            String accountNumber,
            String email
    );
}
