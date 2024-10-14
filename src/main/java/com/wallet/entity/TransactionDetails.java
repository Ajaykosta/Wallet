package com.wallet.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class TransactionDetails {
	@Id
	@SequenceGenerator(name = "trxSeqGen", initialValue = 1, allocationSize = 1, sequenceName = "Trx_Seq")
	@GeneratedValue(generator = "trxSeqGen", strategy = GenerationType.SEQUENCE)
	private Integer trx_id;
	@Column(name = "fromUser", length = 20)
	@NonNull
	private String fromUser;
	@Column(name = "toUser", length = 20)
	@NonNull
	private String toUser;
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "userId")
	private User user;
	@Column(name = "Sent")
	@NonNull
	private Integer amountSent;
	@Column(name = "Received")
	@NonNull
	private Integer amountReceived;
	@NonNull
	@Column(name = "Available_Amount")
	private Integer currentTotalAmount;

	@Override
	public String toString() {
		return "TransactionDetails [trx_id=" + trx_id + ", fromUser=" + fromUser + ", user=" + user + ", amountSent="
				+ amountSent + ", amountReceived=" + amountReceived + ", currentTotalAmount=" + currentTotalAmount
				+ "]";
	}
}
