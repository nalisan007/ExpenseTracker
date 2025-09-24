package io.expensetracker.ExpenseTracker.restApi.dao;

import io.expensetracker.ExpenseTracker.restApi.dto.Transaction;
import io.expensetracker.ExpenseTracker.restApi.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDao {
	@Autowired
	TransactionRepository repo;

	public Transaction saveTransaction(Transaction t) {
		return repo.save(t);
	}

	public List<Transaction> findTransactionByUserId(int userId) {


        return repo.findAllByUserId(userId);

	}

	public Optional<Transaction> findById(long transactionId) {
		return repo.findById(transactionId);
	}

	public Transaction deleteById(long transactionId) {
		Optional<Transaction> tran = repo.findById(transactionId);

		return tran.orElse(null);
	}

	public Integer deleteByUserId(int userId) {

			int recordsDeleted = repo.deleteByUserId(userId);
			return recordsDeleted;

	}
}
