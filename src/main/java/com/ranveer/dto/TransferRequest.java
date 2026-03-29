package com.ranveer.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequest {
	
	private Long senderId;
	private Long receiverId;
	private BigDecimal amount;
	private String idempotencyKey; // New
}
