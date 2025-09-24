package io.expensetracker.ExpenseTracker.restApi.exception;

public class InvalidTransactionException extends RuntimeException {
	private String message;

	public InvalidTransactionException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
