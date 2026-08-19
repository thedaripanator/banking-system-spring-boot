package com.banking.Banking.System.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private  AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;


}
