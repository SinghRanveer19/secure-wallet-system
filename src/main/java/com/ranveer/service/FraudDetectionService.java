package com.ranveer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranveer.entity.Transaction;
import com.ranveer.repository.TransactionRepository;

@Service
public class FraudDetectionService {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	
	// Detect Fraud Activity
	public boolean isFraud(Long senderId, BigDecimal amount) {
		
		// Rule 1 : High Amount
		if (amount.compareTo(new BigDecimal("10000")) > 0) {
			return true;
		}
		
		// Rule 2 : Too many transaction in last 1 minute
		LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
		
		List<Transaction> recentTxns = transactionRepo.findRecentTransaction(senderId, oneMinuteAgo);
		
		if (recentTxns.size() > 3) {
			return true;
		}
		
		return false;
	}
}
