package io.expensetracker.ExpenseTracker.restApi.service;

import io.expensetracker.ExpenseTracker.restApi.dao.TransactionDao;
import io.expensetracker.ExpenseTracker.restApi.dao.UserDao;
import io.expensetracker.ExpenseTracker.restApi.dto.MinimalUserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.PasswordDto;
import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.exception.InvalidUserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private ModelMapper modelMapper;

	public ResponseEntity<UserDto> saveUser(UserDto user) {
		return new ResponseEntity<UserDto>(modelMapper.map(dao.saveUser(user),UserDto.class), HttpStatus.CREATED);

	}
	public ResponseEntity<UserDto> registerUser(Users user) {
		if(!doesUserExist(user.getEmail())) {
			user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
			user.getAccounts().add("Cash");
			user.getCategories().add("Travel");
			return new ResponseEntity<UserDto>(modelMapper.map(dao.registerUser(user),UserDto.class),HttpStatus.CREATED);
		}
		throw new InvalidUserException("User already exists with that email!");
	}

	public ResponseEntity<UserDto> findUserById(int id) {
		Users u = dao.findUserById(id);
		UserDto udto = modelMapper.map(u,UserDto.class);

		if (u != null) {
			return new ResponseEntity<UserDto>(udto, HttpStatus.ALREADY_REPORTED);
		}
		throw new InvalidUserException("No User exist with that User ID");

	}

	public ResponseEntity<UserDto> findUserByEmail(String email) {
		Users u = dao.findUserByEmail(email);
		UserDto udto = modelMapper.map(u,UserDto.class);
		if (u != null) {
			return new ResponseEntity<UserDto>(udto, HttpStatus.OK);
		}
		throw new InvalidUserException("User does not exist!");
	}

	public ResponseEntity<MinimalUserDto> deleteById(int userId) {
		MinimalUserDto u = dao.deleteUserById(userId);
		if (u != null) {

			tdao.deleteByUserId(userId);

			return new ResponseEntity<MinimalUserDto>(u, HttpStatus.OK);
		}
		throw new InvalidUserException("No User exist with that User ID");
	}

	public ResponseEntity<List<MinimalUserDto>> findAllUser() {
		List<MinimalUserDto> udl = dao.findAllUsers();
		return new ResponseEntity<List<MinimalUserDto>>(udl, HttpStatus.MULTI_STATUS);
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
	public ResponseEntity<String> changePassword(PasswordDto pass){

		Users u = dao.findUserById(pass.getUserId());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		if(encoder.encode(pass.getOldPassword()).equals(u.getPassword())){
			u.setPassword(encoder.encode(pass.getNewPassword()));

			return new ResponseEntity<String>("Password Changed Successfully",HttpStatus.OK);

		}
		return new ResponseEntity<String>("Old Password don't match. Password Change Failed!",HttpStatus.BAD_REQUEST);
	}

}
