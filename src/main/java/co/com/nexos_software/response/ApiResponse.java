package co.com.nexos_software.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
	private int code;
	private String message;
	private String cardId;
}
