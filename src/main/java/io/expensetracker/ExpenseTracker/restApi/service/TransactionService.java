package io.expensetracker.ExpenseTracker.restApi.service;

import io.expensetracker.ExpenseTracker.restApi.dao.TransactionDao;
import io.expensetracker.ExpenseTracker.restApi.dao.UserDao;
import io.expensetracker.ExpenseTracker.restApi.dto.Transaction;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.exception.InvalidTransactionException;
import io.expensetracker.ExpenseTracker.restApi.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TransactionService {
	@Autowired
	TransactionDao dao;
	@Autowired
	UserDao udao;

	public ResponseEntity<Transaction> saveTransaction(Transaction t) {
		return new ResponseEntity<Transaction>(dao.saveTransaction(t), HttpStatus.CREATED);
	}

	public ResponseEntity<List<Transaction>> findAllByUserId(int userId) {
		Users u = udao.findUserById(userId);
		if (u == null) {
			throw new InvalidUserException("Invalid User ID / User Not found");
		}
		List<Transaction> tranUId = dao.findTransactionByUserId(userId);
		if (!tranUId.isEmpty())
			return new ResponseEntity<List<Transaction>>(tranUId, HttpStatus.ALREADY_REPORTED);
		throw new InvalidTransactionException("No Transaction Found");

	}

	public ResponseEntity<Transaction> findById(long transactionId) {
		Optional<Transaction> optran = dao.findById(transactionId);
		if (optran.isPresent()) {
			return new ResponseEntity<Transaction>(optran.get(), HttpStatus.OK);
		}
		throw new InvalidTransactionException("Invalid Transaction ID");
	}

	public ResponseEntity<Transaction> deleteById(long transactionId) {
		Transaction tran = dao.deleteById(transactionId);
		if (tran != null) {
			return new ResponseEntity<Transaction>(tran, HttpStatus.OK);
		}
		throw new InvalidTransactionException("Invalid Transaction Id / Transaction Not Found");
	}

	public ResponseEntity<Integer> deleteByUserId(int userId) {
		Users u = udao.findUserById(userId);
		if (u == null) {
			throw new InvalidUserException("User Doesn't exit / Invalid User ID");
		}

		Integer recordsDeleted = dao.deleteByUserId(userId);
		if (recordsDeleted != 0) {
			return new ResponseEntity<Integer>(recordsDeleted, HttpStatus.ALREADY_REPORTED);
		}
		throw new InvalidTransactionException("No Transaction Found");
	}
}
