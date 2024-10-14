package com.wallet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wallet.entity.TransactionDetails;

public interface ITransactionRepository extends JpaRepository<TransactionDetails, Integer> {
	@Query("from TransactionDetails where amountSent is null and user.userId = :id")
	public List<TransactionDetails> getIncomingTransactionById(int id);

	@Query("from TransactionDetails where amountReceived is null and user.userId = :id")
	public List<TransactionDetails> getOutgoingTransactionById(int id);
}
