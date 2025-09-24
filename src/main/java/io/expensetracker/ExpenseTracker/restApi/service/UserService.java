package io.expensetracker.ExpenseTracker.restApi.service;

import io.expensetracker.ExpenseTracker.restApi.dao.TransactionDao;
import io.expensetracker.ExpenseTracker.restApi.dao.UserDao;
import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	UserDao dao;
	@Autowired
	TransactionDao tdao;
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtService jwtService;

	public ResponseEntity<Users> saveUser(Users user) {
		return new ResponseEntity<Users>(dao.saveUser(user), HttpStatus.CREATED);

	}

	public ResponseEntity<Users> findUserById(int id) {
		Users u = dao.findUserById(id);

		if (u != null) {
			return new ResponseEntity<Users>(u, HttpStatus.ALREADY_REPORTED);
		}
		throw new InvalidUserException("No User exist with that User ID");

	}

	public ResponseEntity<Users> findUserByEmail(String email) {
		Users u = dao.findUserByEmail(email);
		if (u != null) {
			return new ResponseEntity<Users>(u, HttpStatus.OK);
		}
		throw new InvalidUserException("User does not exist!");
	}

	public ResponseEntity<UserDto> deleteById(int userId) {
		UserDto u = dao.deleteUserById(userId);
		if (u != null) {

			tdao.deleteByUserId(userId);

			return new ResponseEntity<UserDto>(u, HttpStatus.OK);
		}
		throw new InvalidUserException("No User exist with that User ID");
	}

	public ResponseEntity<List<UserDto>> findAllUser() {
		List<UserDto> udl = dao.findAllUsers();
		return new ResponseEntity<List<UserDto>>(udl, HttpStatus.MULTI_STATUS);
	}
	public String verify(Users user) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
		if(auth.isAuthenticated()){
			return jwtService.generateToken(user);
		}
		return "Login Failed";
	}
	public boolean doesUserExist(String email){
		return dao.doesUserExist(email);
	}

}
