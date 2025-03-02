package co.com.nexos_software.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecargarSaldoRequest {
	
	private String cardId;
	private BigDecimal balance;

}
