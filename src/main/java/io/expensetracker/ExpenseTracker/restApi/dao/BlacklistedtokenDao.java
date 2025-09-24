package io.expensetracker.ExpenseTracker.restApi.dao;

import io.expensetracker.ExpenseTracker.restApi.dto.BlacklistedToken;
import io.expensetracker.ExpenseTracker.restApi.repo.BlacklistedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public class BlacklistedtokenDao {
    @Autowired
    BlacklistedTokenRepository repo;

    public void addToBlacklist(BlacklistedToken blackToken) {
        repo.save(blackToken);

    }

    public Optional<Date> findExpiryByToken(String token){
        return repo.findExpiryByToken(token);
    }

    public void deleteByToken(String token){
        repo.deleteByToken(token);
    }
    public int deleteExpiredTokens(){
        return repo.deleteByExpiryBefore(new Date());
    }


}
