package co.com.nexos_software.dto;

import lombok.Data;

@Data
public class TransactionRequest {
	
    private String cardId;
    private double price;
    private String transactionId; 
}