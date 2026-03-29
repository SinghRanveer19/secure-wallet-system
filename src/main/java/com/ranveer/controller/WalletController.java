package com.ranveer.controller;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ranveer.dto.TransferRequest;
import com.ranveer.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@GetMapping("/balance/{userId}")
	public BigDecimal getBalance(@PathVariable Long userId) {
		return walletService.getBalance(userId);
	}
	
	@PostMapping("/transfer")
	public String transfer(@RequestBody TransferRequest request) {
		walletService.transfer(request);
		return "Transfer successful";
		
	}
	
	@PostMapping("/add-money")
	public String addMoney(@RequestParam Long walletId, @RequestParam BigDecimal amount) {
		walletService.addMoney(walletId, amount);
		return "Money added";
	}
}
