package com.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.dao.ITransactionRepository;
import com.wallet.dao.IUserAuthenticationRepository;
import com.wallet.dao.IUserRepository;
import com.wallet.entity.TransactionDetails;
import com.wallet.entity.User;
import com.wallet.entity.UserAuthentication;
import com.wallet.exception.InsufficientAmountException;
import com.wallet.exception.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service("walletService")
public class UserManagementServiceImplimentation implements IUserManagementService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ITransactionRepository transactionRepository;
	@Autowired
	private IUserAuthenticationRepository authenticationRepository;

	@Override
	public String registerUser(User user) {
		return "User Registered with " + userRepository.save(user).getUserId() + " Account Number";
	}

	@Override
	public String loginUser(int userId, String password) {
		Optional<UserAuthentication> opt = authenticationRepository.findById(userId);
		if (opt.isEmpty())
			return "User Not found";
		else {
			UserAuthentication user = opt.get();
			if (user.getPassword().equals(password)) {
				return "Authenticated";
			} else {
				return "Wrong Password";
			}
		}
	}

	@Override
	public List<User> getAllUsersDetails() {
		return userRepository.findAll();
	}

	@Override
	public User getUserDetailsById(int id) throws UserNotFoundException {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isEmpty())
			throw new UserNotFoundException("User with ID " + id + " not found");
		else {
			return opt.get();
		}
	}

	@Override
	public List<TransactionDetails> getTransactionDetailsById(int id) throws UserNotFoundException {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isEmpty())
			throw new UserNotFoundException("User with ID " + id + " not found");
		else {
			return opt.get().getTransactionDetails();
		}
	}

	@Override
	public int getAvailableAmountOfUserById(int id) {
		User user = userRepository.getReferenceById(id);
		return user.getBalance();
	}

	@Override
	public List<TransactionDetails> incomingTransactionOfUserById(int id) {
		return transactionRepository.getIncomingTransactionById(id);
	}

	@Override
	public List<TransactionDetails> outgoingTransactionOfUserById(int id) {
		return transactionRepository.getOutgoingTransactionById(id);
	}

	@Override
	@Transactional
	public String transferMoney(int senderAccountNumber, int amount, int receiverAccountNumber)
			throws UserNotFoundException, InsufficientAmountException {
		Optional<User> opt = userRepository.findById(receiverAccountNumber);
		if (opt.isEmpty())
			throw new UserNotFoundException("User with ID " + receiverAccountNumber + " not found");
		else {
			User sender = userRepository.getReferenceById(senderAccountNumber);
			User receiver = opt.get();
			if (sender.getBalance() < amount) {
				throw new InsufficientAmountException();
			} else {
				receiver.setBalance(receiver.getBalance() + amount);
				sender.setBalance(sender.getBalance() - amount);
				TransactionDetails senderTransaction = new TransactionDetails(sender.getUserName(),
						receiver.getUserName(), amount, 0, sender.getBalance());
				TransactionDetails receiverTransaction = new TransactionDetails(sender.getUserName(),
						receiver.getUserName(), 0, amount, receiver.getBalance());
				sender.getTransactionDetails().add(senderTransaction);
				receiver.getTransactionDetails().add(receiverTransaction);
				userRepository.save(sender);
				userRepository.save(receiver);
				transactionRepository.save(senderTransaction);
				transactionRepository.save(receiverTransaction);
				return "Money tranfered successfully";
			}
		}
	}

	@Override
	public String addMoneyToWallet(int id, int amount) {
		Optional<User> opt = userRepository.findById(id);
		if (opt.isEmpty())
			return "User not found";
		else {
			User user = opt.get();
			user.setBalance(user.getBalance() + amount);
			userRepository.save(user);
			return "Money added Successfully";
		}
	}
}
