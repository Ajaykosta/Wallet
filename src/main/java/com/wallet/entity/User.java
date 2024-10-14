package com.wallet.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "WALLET_USER")
@Setter
@Getter
@RequiredArgsConstructor
public class User {
	@Id
	@SequenceGenerator(name = "SeqGen", initialValue = 1, allocationSize = 1, sequenceName = "User_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGen")
	private Integer userId;
	@Column(name = "Name", length = 20)
	@NonNull
	private String userName;
	@Column(name = "Balance")
	@NonNull
	private Integer balance;
	@OneToMany(targetEntity = TransactionDetails.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<TransactionDetails> transactionDetails;
	@NonNull
	private Boolean isAdmin;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NonNull
	private UserAuthentication userAuthentication;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", balance=" + balance + ", trnsactionDetails="
				+ transactionDetails + ", isAdmin=" + isAdmin + ", userAuthentication=" + userAuthentication + "]";
	}
}
