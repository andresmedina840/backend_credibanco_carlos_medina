package co.com.nexos_software.controller;

import co.com.nexos_software.entity.Transaction;
import co.com.nexos_software.exception.*;
import co.com.nexos_software.request.AnularTransaccionRequest;
import co.com.nexos_software.request.TransaccionCompraRequest;
import co.com.nexos_software.response.ApiResponse;
import co.com.nexos_software.response.ConsultaTransaccionResponse;
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

	@PostMapping("/purchase")
	public ResponseEntity<?> purchase(@RequestBody TransaccionCompraRequest transaccionCompraRequest) {
		try {

			System.out.println("valorrr: " + transaccionCompraRequest.getPrice());
			String result = transactionService.processPurchase(transaccionCompraRequest.getCardId(),
					transaccionCompraRequest.getPrice());
			return ResponseEntity.ok(Map.of("code", 0, "message", result));

		} catch (CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));
		} catch (InvalidCardOperationException | InvalidAmountException e) {
			return ResponseEntity.badRequest().body(Map.of("code", 1, "message", e.getMessage()));
		}
	}

	@GetMapping("/{transactionId}")
	public ResponseEntity<?> getTransaction(@PathVariable Long transactionId) {
		try {
			Transaction transaction = transactionService.getTransaction(transactionId);
			return ResponseEntity.ok(new ConsultaTransaccionResponse(0, "Tarjeta activada", transaction));
		} catch (TransactionNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1, e.getMessage(), null));
		}
	}

	@PostMapping("/anulation")
	public ResponseEntity<?> annulTransaction(@RequestBody AnularTransaccionRequest anularTransaccionRequest) {
		try {
			String result = transactionService.annulTransaction(anularTransaccionRequest.getCardId(),
					anularTransaccionRequest.getTransactionId());
			return ResponseEntity.ok(Map.of("code", 0, "message", result));
		} catch (TransaccionNoEncontradaException | CardNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", 1, "message", e.getMessage()));
		} catch (InvalidTransactionOperationException e) {
			return ResponseEntity.badRequest().body(Map.of("code", 1, "message", e.getMessage()));
		}
	}
}