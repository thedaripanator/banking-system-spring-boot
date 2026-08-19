package com.banking.Banking.System.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true , nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @CreationTimestamp
    private LocalDateTime createdAt;

}
