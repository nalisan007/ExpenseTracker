package io.expensetracker.ExpenseTracker.restApi.service;

import io.expensetracker.ExpenseTracker.restApi.dao.BlacklistedtokenDao;
import io.expensetracker.ExpenseTracker.restApi.dto.BlacklistedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BlacklistService {
    @Autowired
    BlacklistedtokenDao dao;



        public void addToBlacklist(String token, Date expiry) {

            dao.addToBlacklist(new BlacklistedToken(token,expiry));

        }

        public boolean isBlacklisted(String token) {
            Optional<Date> expiry = dao.findExpiryByToken(token);
            if (expiry.isEmpty())
                return false;


            if (expiry.get().before(new Date())) {
                dao.deleteByToken(token);
                return false;
            }
            return true;
        }
        public int deleteExpiredTokens() {
            return dao.deleteExpiredTokens();
        }


}
