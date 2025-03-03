package co.com.nexos_software.service;

import co.com.nexos_software.entity.Card;
import co.com.nexos_software.exception.CardNotFoundException;
import co.com.nexos_software.exception.InvalidAmountException;
import co.com.nexos_software.exception.InvalidCardOperationException;
import co.com.nexos_software.repository.CardRepository;
import co.com.nexos_software.util.NameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;

	public Map<String, String> generateCardNumber(String productId) {
		if (!productId.matches("\\d{6}")) {
			throw new IllegalArgumentException("Product ID inválido: Debe ser 6 dígitos");
		}

		String holderName = NameGenerator.generarNombreTitular(); 
		String cardNumber;
		do {
			long random = ThreadLocalRandom.current().nextLong(0, 10_000_000_000L);
			cardNumber = productId + String.format("%010d", random);
		} while (cardRepository.existsById(cardNumber));

		Card newCard = Card.builder().number(cardNumber).holderName(holderName)
				.balance(BigDecimal.ZERO).isActive(false).isBlocked(false).build();

		cardRepository.save(newCard);
		return Map.of("cardNumber", cardNumber, "holderName", holderName);
	}

	public void activateCard(String cardNumber) {
		Card card = cardRepository.findById(cardNumber)
				.orElseThrow(() -> new CardNotFoundException("Tarjeta no encontrada: " + cardNumber));

		if (card.isBlocked()) {
			throw new InvalidCardOperationException("Tarjeta bloqueada - No se puede activar");
		}

		if (card.isActive()) {
			throw new InvalidCardOperationException("Tarjeta ya está activa");
		}

		card.setActive(true);
		cardRepository.save(card);
	}

	public void blockCard(String cardNumber) {
		Card card = cardRepository.findById(cardNumber)
				.orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

		if (card.isBlocked()) {
			throw new RuntimeException("Tarjeta ya está bloqueada");
		}

		card.setBlocked(true);
		cardRepository.save(card);
	}

	public void rechargeBalance(String cardNumber, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException("Monto debe ser mayor a 0");
		}

		Card card = cardRepository.findById(cardNumber)
				.orElseThrow(() -> new CardNotFoundException("Tarjeta no encontrada: " + cardNumber));

		if (!card.isActive()) {
			throw new InvalidCardOperationException("Tarjeta inactiva - Active primero");
		}

		if (card.isBlocked()) {
			throw new InvalidCardOperationException("Tarjeta bloqueada");
		}

		if (LocalDate.now().isAfter(card.getExpiryDate())) {
			throw new InvalidCardOperationException("Tarjeta vencida");
		}

		card.setBalance(card.getBalance().add(amount));
		cardRepository.save(card);
	}

	public BigDecimal getBalance(String cardNumber) {
		Card card = cardRepository.findById(cardNumber)
				.orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
		return card.getBalance();
	}
}