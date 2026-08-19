package com.banking.Banking.System.Repository;

import com.banking.Banking.System.Model.Transaction;
import com.banking.Banking.System.Model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByFromAccountOrToAccount(
            String fromAccount,
            String toAccount
    );

    Page<Transaction> findByFromAccountOrToAccount(
            String fromAccount,
            String toAccount,
            Pageable pageable
    );

    @Query("""
    SELECT t FROM Transaction t
    WHERE t.type = :type
    AND (t.fromAccount = :accountNumber
         OR t.toAccount = :accountNumber)
""")
    Page<Transaction> findByAccountAndType(
            @Param("accountNumber") String accountNumber,
            @Param("type") TransactionType type,
            Pageable pageable
    );
}
