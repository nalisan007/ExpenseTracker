package io.expensetracker.ExpenseTracker.restApi.controller;

import io.expensetracker.ExpenseTracker.restApi.dto.MinimalUserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.PasswordDto;
import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping
	public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto user) {
		return service.saveUser(user);

	}


	@GetMapping
	public ResponseEntity<UserDto> findUserById(@RequestParam @Positive int id) {
		return service.findUserById(id);
	}

	@GetMapping("email")
	public ResponseEntity<UserDto> findUserByEmail(@RequestParam @Email String email) {
		return service.findUserByEmail(email);
	}

	@DeleteMapping
	public ResponseEntity<MinimalUserDto> deleteUserById(@RequestParam @Positive int id) {
		return service.deleteById(id);
	}

	@GetMapping("all")
	public ResponseEntity<List<MinimalUserDto>> findAllUser() {
		return service.findAllUser();
	}

	@PostMapping("changePassword")
	public ResponseEntity<String> changePassword(@RequestBody PasswordDto pass){
		return service.changePassword(pass);
	}
}
