package co.com.nexos_software.controller;

import co.com.nexos_software.exception.CardNotFoundException;
import co.com.nexos_software.exception.InvalidAmountException;
import co.com.nexos_software.exception.InvalidCardOperationException;
import co.com.nexos_software.request.ActivarTarjetaRequest;
import co.com.nexos_software.request.RecargarSaldoRequest;
import co.com.nexos_software.response.ApiResponse;
import co.com.nexos_software.response.CreacionTarjetaResponse;
import co.com.nexos_software.service.CardService;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/card")
public class CardController {

	private final CardService cardService;

	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping("/{productId}/number")
	public ResponseEntity<?> generateCardNumber(@PathVariable String productId) {
		try {
			Map<String, String> cardData = cardService.generateCardNumber(productId);
			return ResponseEntity.ok(new CreacionTarjetaResponse(0,
					"Tarjeta generadaaaaaaaa No. " + cardData.get("cardNumber"), cardData.get("holderName")));

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(-1, e.getMessage(), null));
		}
	}

	@PostMapping("/enroll")
	public ResponseEntity<?> activateCard(@RequestBody ActivarTarjetaRequest activarTarjetaRequest) {
		try {
			cardService.activateCard(activarTarjetaRequest.getCardId());
			return ResponseEntity.ok(Map.of("code", 0, "message", "Tarjeta activada"));
		} catch (CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));

		} catch (InvalidCardOperationException e) {
			return ResponseEntity.badRequest().body(Map.of("code", 1, "message", e.getMessage()));
		}
	}

	@DeleteMapping("/{cardNumber}")
	public ResponseEntity<?> blockCard(@PathVariable String cardNumber) {
		try {
			cardService.blockCard(cardNumber);
			return ResponseEntity
					.ok(Map.of("code", 0, "message", "La Tarjeta No. " + cardNumber + " ha sido bloqueada"));

		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));
		}
	}

	@PostMapping("/balance")
	public ResponseEntity<?> rechargeBalance(@RequestBody RecargarSaldoRequest recargarSaldoRequest) {
		try {
			cardService.rechargeBalance(recargarSaldoRequest.getCardId(), recargarSaldoRequest.getBalance());
			return ResponseEntity
					.ok(Map.of("code", 0, "message", "Saldo recargado de: " + recargarSaldoRequest.getBalance()));
		} catch (CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));
		} catch (InvalidCardOperationException | InvalidAmountException e) {
			return ResponseEntity.badRequest().body(Map.of("code", 1, "message", e.getMessage()));
		}
	}

	@GetMapping("/balance/{cardNumber}")
	public ResponseEntity<?> getBalance(@PathVariable String cardNumber) {
		try {
			BigDecimal balance = cardService.getBalance(cardNumber);
			return ResponseEntity.ok(Map.of("code", 0, "message", "Tiene un saldo de: " + balance));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1, e.getMessage(), null));
		}
	}
}