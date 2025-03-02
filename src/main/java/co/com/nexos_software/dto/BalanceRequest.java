package co.com.nexos_software.dto;

import lombok.Data;

@Data
public class BalanceRequest {
	
    private String cardId;
    private double balance;
    
}