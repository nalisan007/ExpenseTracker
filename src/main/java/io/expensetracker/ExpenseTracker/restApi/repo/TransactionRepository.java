package io.expensetracker.ExpenseTracker.restApi.repo;

import io.expensetracker.ExpenseTracker.restApi.dto.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findAllByUserId(Integer userId, Sort sort);

	public int deleteByUserId(Integer userId);
}
