package com.vena.ecom.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
