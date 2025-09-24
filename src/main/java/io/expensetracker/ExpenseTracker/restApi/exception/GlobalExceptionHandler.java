package io.expensetracker.ExpenseTracker.restApi.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<String> handleInvalidUserExc(InvalidUserException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<String> handleInvalidTransactionExc(InvalidTransactionException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException exc) {
		List<String> errors = exc.getConstraintViolations().stream()
				.map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList();
		return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler
	public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
		List<String> fieldMessages = exc.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();
		return new ResponseEntity<List<String>>(fieldMessages, HttpStatus.BAD_REQUEST);
	}
}
