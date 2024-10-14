package com.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.LoginRequest;
import com.wallet.entity.TransactionDetails;
import com.wallet.entity.User;
import com.wallet.exception.InsufficientAmountException;
import com.wallet.exception.UserNotFoundException;
import com.wallet.service.IUserManagementService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api")
@Validated
public class WalletController {

	@Autowired
	private IUserManagementService userManagementService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Register a new user
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Validated @RequestBody User user) {
		// Encrypt the password before saving
		user.getUserAuthentication().setPassword(passwordEncoder.encode(user.getUserAuthentication().getPassword()));
		String message = userManagementService.registerUser(user);
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	// User login
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@Validated @RequestBody LoginRequest loginRequest) {
		String message = userManagementService.loginUser(loginRequest.getUserId(), loginRequest.getPassword());
		if (message.equals("Authenticated")) {
			return new ResponseEntity<>(message, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		}
	}

	// Get all users
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsersDetails() {
		List<User> users = userManagementService.getAllUsersDetails();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Get user by ID
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserDetailsById(@PathVariable @Min(1) int id) throws UserNotFoundException {
		User user = userManagementService.getUserDetailsById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Transfer money
	@PostMapping("/transfer")
	public ResponseEntity<String> transferMoney(@RequestParam @Min(1) int senderAccountNumber,
			@RequestParam @Min(1) int receiverAccountNumber, @RequestParam @Min(1) int amount) {
		try {
			String message = userManagementService.transferMoney(senderAccountNumber, amount, receiverAccountNumber);
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (UserNotFoundException | InsufficientAmountException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Add money to wallet
	@PostMapping("/wallet/{id}/addMoney")
	public ResponseEntity<String> addMoneyToWallet(@PathVariable @Min(1) int id, @RequestParam @Min(1) int amount) {
		String message = userManagementService.addMoneyToWallet(id, amount);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	// Incoming transactions for a user
	@GetMapping("/transactions/{id}/incoming")
	public ResponseEntity<List<TransactionDetails>> incomingTransactionOfUserById(@PathVariable @Min(1) int id) {
		List<TransactionDetails> transactions = userManagementService.incomingTransactionOfUserById(id);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	// Outgoing transactions for a user
	@GetMapping("/transactions/{id}/outgoing")
	public ResponseEntity<List<TransactionDetails>> outgoingTransactionOfUserById(@PathVariable @Min(1) int id) {
		List<TransactionDetails> transactions = userManagementService.outgoingTransactionOfUserById(id);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
}
