package io.expensetracker.ExpenseTracker.restApi.repo;

import io.expensetracker.ExpenseTracker.restApi.dto.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken,Integer> {
    public Optional<BlacklistedToken> findByToken(String token);

    @Query("SELECT t.expiry FROM BlacklistedToken t WHERE t.token = :token")
     public Optional<Date> findExpiryByToken(@Param("token") String token);

     public void deleteByToken(String token);

     public int deleteByExpiryBefore(Date expiry);

}
