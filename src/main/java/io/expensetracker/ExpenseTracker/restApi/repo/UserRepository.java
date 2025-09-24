package io.expensetracker.ExpenseTracker.restApi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.expensetracker.ExpenseTracker.restApi.dto.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	public Optional<Users> findByEmail(String email);

	public Optional<Users> findUsersByEmail(String email);

    public boolean existsByEmail(String email);
}
