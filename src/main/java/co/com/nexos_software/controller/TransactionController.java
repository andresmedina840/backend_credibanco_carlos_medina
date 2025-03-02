package co.com.nexos_software.controller;

import co.com.nexos_software.entity.Transaction;
import co.com.nexos_software.exception.*;
import co.com.nexos_software.request.TransaccionCompraRequest;
import co.com.nexos_software.response.ApiResponse;
import co.com.nexos_software.service.TransactionService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/transaction")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// 1. Realizar compra
	@PostMapping("/purchase")
	public ResponseEntity<?> purchase(@RequestBody TransaccionCompraRequest transaccionCompraRequest) {
		try {
			String result = transactionService.processPurchase(transaccionCompraRequest.getCardId(),
					transaccionCompraRequest.getPrecio());
			return ResponseEntity.ok(Map.of("code", 0, "message", result));

		} catch (CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));
		} catch (InvalidCardOperationException | InvalidAmountException e) {
			return ResponseEntity.badRequest().body(Map.of("code", 1, "message", e.getMessage()));
		}
	}

	// 2. Anular transacción
	@PostMapping("/anulation")
	public ResponseEntity<ApiResponse> annulTransaction(@RequestParam String cardNumber,
			@RequestParam Long transactionId) {
		try {
			String result = transactionService.annulTransaction(cardNumber, transactionId);
			return ResponseEntity.ok(new ApiResponse(0, result, transactionId.toString()));
		} catch (TransactionNotFoundException | CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1, e.getMessage(), null));
		} catch (InvalidTransactionOperationException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(-1, e.getMessage(), null));
		}
	}

	// 3. Consultar transacción
	@GetMapping("/{transactionId}")
	public ResponseEntity<?> getTransaction(@PathVariable Long transactionId) {
		try {
			Transaction transaction = transactionService.getTransaction(transactionId);
			return ResponseEntity.ok(Map.of("code", 0, "message", "Tarjeta activada " + transaction));
		} catch (TransactionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1, e.getMessage(), null));
		}
	}
}