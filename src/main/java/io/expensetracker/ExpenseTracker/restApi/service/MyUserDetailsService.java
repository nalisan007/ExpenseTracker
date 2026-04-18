package io.expensetracker.ExpenseTracker.restApi.service;

import io.expensetracker.ExpenseTracker.restApi.dto.UserPrincipal;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> opuser = repo.findByEmail(username);
        Users user = opuser.orElseThrow(()->new UsernameNotFoundException(username + " not found"));


        return new UserPrincipal(user);
    }
}
