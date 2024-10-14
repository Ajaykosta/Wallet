package com.wallet.service;

import java.util.List;

import com.wallet.entity.TransactionDetails;
import com.wallet.entity.User;
import com.wallet.exception.InsufficientAmountException;
import com.wallet.exception.UserNotFoundException;

public interface IUserManagementService {
	public String registerUser(User user);

	public String loginUser(int userId, String password);

	public List<User> getAllUsersDetails();

	public User getUserDetailsById(int id) throws UserNotFoundException;

	public List<TransactionDetails> getTransactionDetailsById(int id) throws UserNotFoundException;

	public int getAvailableAmountOfUserById(int id);

	public List<TransactionDetails> incomingTransactionOfUserById(int id);

	public List<TransactionDetails> outgoingTransactionOfUserById(int id);

	public String transferMoney(int senderAccountNumber, int amount, int receiverAccountNumber)
			throws UserNotFoundException, InsufficientAmountException;

	public String addMoneyToWallet(int id, int amount);
}