package co.com.nexos_software.exception;

public class InvalidCardOperationException extends RuntimeException {

	private static final long serialVersionUID = -7503044448697885936L;

	public InvalidCardOperationException(String message) {
        super(message);
    }
}