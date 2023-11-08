package com.bank.bankproject.repository;

import com.bank.bankproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user FROM User user " +
            "WHERE user.email =:email")
    Optional<User> findByEmail(@Param("email") String email);
}
