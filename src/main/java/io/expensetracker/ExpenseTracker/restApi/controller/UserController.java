package io.expensetracker.ExpenseTracker.restApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.expensetracker.ExpenseTracker.restApi.dto.UserDto;
import io.expensetracker.ExpenseTracker.restApi.dto.Users;
import io.expensetracker.ExpenseTracker.restApi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService service;

	@PostMapping
	public ResponseEntity<Users> saveUser(@Valid @RequestBody Users user) {
		return service.saveUser(user);

	}

	@GetMapping
	public ResponseEntity<Users> findUserById(@RequestParam @Positive int id) {
		return service.findUserById(id);
	}

	@GetMapping("email")
	public ResponseEntity<Users> findUserByEmail(@RequestParam @Email String email) {
		return service.findUserByEmail(email);
	}

	@DeleteMapping
	public ResponseEntity<UserDto> deleteUserById(@RequestParam @Positive int id) {
		return service.deleteById(id);
	}

	@GetMapping("all")
	public ResponseEntity<List<UserDto>> findAllUser() {
		return service.findAllUser();
	}

}
