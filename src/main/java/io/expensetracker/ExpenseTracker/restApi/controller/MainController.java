package io.expensetracker.ExpenseTracker.restApi.controller;

import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.service.BlacklistService;
import io.expensetracker.ExpenseTracker.restApi.service.JwtService;
import io.expensetracker.ExpenseTracker.restApi.service.MyUserDetailsService;
import io.expensetracker.ExpenseTracker.restApi.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;
    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    BlacklistService blacklistService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("register")
    public ResponseEntity<Users> register(@RequestBody Users user){

        if(!userService.doesUserExist(user.getEmail())) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.getAccounts().add("Cash");
            user.getCategories().add("Travel");
            return userService.saveUser(user);
        }
//        return new ResponseEntity<String>("User Already Exist!",HttpStatus.BAD_REQUEST);
        return null;
    }

    @PostMapping("auth/login")
    public String login(@RequestBody Users user) {

         return userService.verify(user);
    }

    @GetMapping("/")
    public String greet(HttpServletRequest req){
        return "Welcome. Your Session ID is : " + req.getSession().getId();
    }
    @PostMapping("auth/logout")
    public ResponseEntity<String> logout(HttpServletRequest req, Authentication authentication){
        String authHeader = req.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
                if(username != null) {
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(token, userDetails)) {
                        blacklistService.addToBlacklist(token, jwtService.extractClaim(token, Claims::getExpiration));

                        return new ResponseEntity<String>("Logged out Successfully", HttpStatus.OK);
                    }
                }
            }
        }
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("authentication.name == 'admin@admin.com'")
    @GetMapping("deleteExpiredToken")
    @Transactional
    public int deleteExpiredToken(){
        return blacklistService.deleteExpiredTokens();

    }
}

