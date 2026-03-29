package com.ranveer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.ranveer.entity.Transaction;
import com.ranveer.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;

	
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTransaction(Transaction txn) {
		transactionRepo.save(txn);
	}
}
