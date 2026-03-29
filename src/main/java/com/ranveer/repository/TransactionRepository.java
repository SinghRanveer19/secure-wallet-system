package com.ranveer.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ranveer.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<Transaction> findByIdempotencyKey(String key);
	
	@Query("SELECT t FROM Transaction t WHERE t.senderWalletId = :senderId AND t.createdAt > :time")
	List<Transaction> findRecentTransaction(Long senderId, LocalDateTime time);
}
