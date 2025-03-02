package co.com.nexos_software.service;

import co.com.nexos_software.entity.Card;
import co.com.nexos_software.entity.Transaction;
import co.com.nexos_software.exception.CardNotFoundException;
import co.com.nexos_software.exception.InvalidCardOperationException;
import co.com.nexos_software.exception.InvalidTransactionOperationException;
import co.com.nexos_software.exception.SaldoInsuficienteException;
import co.com.nexos_software.exception.TarjetVencidaException;
import co.com.nexos_software.exception.TarjetaBloqueadaException;
import co.com.nexos_software.repository.CardRepository;
import co.com.nexos_software.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final CardRepository cardRepository;

	public TransactionService(TransactionRepository transactionRepository, CardRepository cardRepository) {
		this.transactionRepository = transactionRepository;
		this.cardRepository = cardRepository;
	}

	// Procesar compra
	public String processPurchase(String cardNumber, double amount) {
		Card card = cardRepository.findById(cardNumber)
	            .orElseThrow(() -> new CardNotFoundException("Tarjeta no encontrada")); 
		
		// ... validaciones
	    if (!card.isActive()) {
	        throw new InvalidCardOperationException("Tarjeta inactiva");
	    }

		// Convertir el monto a BigDecimal
		BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);

		// Validaciones
		if (!card.isActive())
			throw new InvalidCardOperationException("Tarjeta inactiva");
		if (card.isBlocked())
			throw new TarjetaBloqueadaException("Tarjeta bloqueada");
		if (card.getExpiryDate().isBefore(LocalDate.now()))
			throw new TarjetVencidaException("Tarjeta vencida");
		if (card.getBalance().compareTo(amountBigDecimal) < 0) {
			throw new SaldoInsuficienteException("Saldo insuficiente");
		}

		// Actualizar saldo usando BigDecimal
		card.setBalance(card.getBalance().subtract(amountBigDecimal)); // ✅ Método subtract
		cardRepository.save(card);

		// Crear transacción
		Transaction transaction = Transaction.builder().card(card).amount(amount).timestamp(LocalDateTime.now())
				.status("APPROVED").build();
		transactionRepository.save(transaction);

		return "Compra exitosa. Número de transacción: " + transaction.getId();
	}

	// Anular transacción
	public String annulTransaction(String cardNumber, Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

		if (!transaction.getCard().getNumber().equals(cardNumber)) {
	        throw new InvalidTransactionOperationException("La transacción no pertenece a esta tarjeta");
	    }

		// Convertir el monto a BigDecimal
		BigDecimal amountBigDecimal = BigDecimal.valueOf(transaction.getAmount());

		// Reembolsar saldo
		Card card = cardRepository.findById(cardNumber)
				.orElseThrow(() -> new CardNotFoundException("Tarjeta no encontrada"));
		card.setBalance(card.getBalance().add(amountBigDecimal)); // ✅ Método add
		cardRepository.save(card);

		transaction.setStatus("ANNULLED");
		transactionRepository.save(transaction);
		return "Transacción anulada. ID: " + transaction.getId();
	}

	// Consultar transacción
	public Transaction getTransaction(Long transactionId) {
		return transactionRepository.findById(transactionId)
				.orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
	}
}
