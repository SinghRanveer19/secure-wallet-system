package com.ranveer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ranveer.dto.TransferRequest;
import com.ranveer.entity.Transaction;
import com.ranveer.entity.Wallet;
import com.ranveer.repository.TransactionRepository;
import com.ranveer.repository.WalletRepository;

import jakarta.transaction.Transactional;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private FraudDetectionService fraudDetectionService;
	
	@Autowired
	private TransactionService transactionService;

	
	public BigDecimal getBalance(Long userId) {
		Wallet wallet = walletRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Wallet not found"));
		return wallet.getBalance();
		
	}
	
	
	@Transactional
	public void transfer(TransferRequest request) {
		
		// 1. Check if already processed
		Optional<Transaction> existingTxn = transactionRepo.findByIdempotencyKey(request.getIdempotencyKey());
		
		if (existingTxn.isPresent()) {
			return; // Ignore duplicate request
		}
		Wallet sender = walletRepo.findById(request.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
		
		Wallet receiver = walletRepo.findById(request.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
		
		BigDecimal amount = request.getAmount();
		
		String idempotencyKey = request.getIdempotencyKey();
		
		// check balance
		if (sender.getBalance().compareTo(amount) < 0) {
			throw new RuntimeException("Insufficient balance");
		}
		
		// Deduct
		sender.setBalance(sender.getBalance().subtract(amount));
		
		// Add
		receiver.setBalance(receiver.getBalance().add(amount));
		
		walletRepo.save(sender);
		walletRepo.save(receiver);
		
		
		// Fraud Transaction
		if (fraudDetectionService.isFraud(sender.getId(), amount)) {
			
			Transaction txn = new Transaction();
			txn.setSenderWalletId(sender.getId());
			txn.setReceiverWalletId(receiver.getId());
			txn.setAmount(amount);
			txn.setStatus("SUSPICIOUS");
			txn.setCreatedAt(LocalDateTime.now());
			txn.setIdempotencyKey(idempotencyKey);
			
			transactionService.saveTransaction(txn);
			
			throw new RuntimeException("Transaction Flagged as suspicious");
		}
		
		
		// Save Transaction (Normal)
		Transaction txn = new Transaction();
		txn.setSenderWalletId(sender.getId());
		txn.setReceiverWalletId(receiver.getId());
		txn.setAmount(amount);
		txn.setStatus("SUCCESS");
		txn.setCreatedAt(LocalDateTime.now());
		txn.setIdempotencyKey(idempotencyKey);
		
		transactionRepo.save(txn);
		
	}
	
	@Transactional
	public void addMoney(Long walletId, BigDecimal amount) {
		
		Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet not found "));
		
		wallet.setBalance(wallet.getBalance().add(amount));
		
		walletRepo.save(wallet);
	}
	
}
