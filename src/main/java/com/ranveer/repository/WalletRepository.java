package com.ranveer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranveer.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
	Optional<Wallet> findByUserId(Long userId);
}
