package com.ranveer.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ranveer.entity.User;
import com.ranveer.entity.Wallet;
import com.ranveer.repository.UserRepository;
import com.ranveer.repository.WalletRepository;
import com.ranveer.util.JwtUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private WalletRepository walletRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public User register(User user) {
		
		// Encrypt password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Save user
		User savedUser = userRepo.save(user);
		
		// Create Wallet Automatically
		Wallet wallet = new Wallet();
		wallet.setUser(savedUser);
		wallet.setBalance(new BigDecimal("1000"));
		
		walletRepo.save(wallet);
		
		return savedUser;
	}
	
	public String login(String email, String password) {
		User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		return jwtUtil.generateToken(user.getEmail());
	}
}
