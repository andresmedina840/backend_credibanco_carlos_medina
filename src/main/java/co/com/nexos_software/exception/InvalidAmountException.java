package co.com.nexos_software.exception;

public class InvalidAmountException extends RuntimeException {

	private static final long serialVersionUID = -1017223490853986839L;

	public InvalidAmountException(String message) {
		super(message);
	}
}