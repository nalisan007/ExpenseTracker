package io.expensetracker.ExpenseTracker.restApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.expensetracker.ExpenseTracker.restApi.dto.Transaction;
import io.expensetracker.ExpenseTracker.restApi.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("transaction")
public class TransactionController {
	@Autowired
	TransactionService service;

	@PostMapping
	public ResponseEntity<Transaction> saveTransaction(@Valid @RequestBody Transaction t) {
		return service.saveTransaction(t);
	}

	@GetMapping("all")
	public ResponseEntity<List<Transaction>> findAllByUserId(@RequestParam @Positive int userId) {
		return service.findAllByUserId(userId);
	}

	@GetMapping
	public ResponseEntity<Transaction> findById(@RequestParam @Positive long transactionId) {
		return service.findById(transactionId);
	}

	@DeleteMapping
	public ResponseEntity<Transaction> deleteById(@RequestParam @Positive long transactionId) {
		return service.deleteById(transactionId);
	}

	@DeleteMapping("deleteAll")
	public ResponseEntity<Integer> deleteByUserId(@RequestParam @Positive int userId) {
		return service.deleteByUserId(userId);
	}

}
