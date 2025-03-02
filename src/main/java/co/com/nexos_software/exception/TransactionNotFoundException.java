package co.com.nexos_software.exception;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4880630618945373379L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
