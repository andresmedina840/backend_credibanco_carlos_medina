package co.com.nexos_software.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AnularTransaccionRequest {
	
	private String cardId;
	private Long transactionId;

}
