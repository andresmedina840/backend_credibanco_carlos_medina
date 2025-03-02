package co.com.nexos_software.exception;

public class InvalidTransactionOperationException extends RuntimeException {

	private static final long serialVersionUID = -8636503819282075607L;

	public InvalidTransactionOperationException(String message) {
		super(message);
	}
}
