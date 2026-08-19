package com.banking.Banking.System.Repository;

import com.banking.Banking.System.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
}
