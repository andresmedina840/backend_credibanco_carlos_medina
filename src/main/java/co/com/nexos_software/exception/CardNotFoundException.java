package co.com.nexos_software.exception;

public class CardNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8724028886284962717L;

	public CardNotFoundException(String message) {
		super(message);
	}
}
