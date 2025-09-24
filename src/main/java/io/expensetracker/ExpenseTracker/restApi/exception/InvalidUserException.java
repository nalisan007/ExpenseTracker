package io.expensetracker.ExpenseTracker.restApi.exception;

public class InvalidUserException extends RuntimeException {
	private String message;

	public String getMessage() {
		return message;
	}

	public InvalidUserException(String message) {
		super();
		this.message = message;
	}

}
