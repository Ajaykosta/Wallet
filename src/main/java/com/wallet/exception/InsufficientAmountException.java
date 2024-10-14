package com.wallet.exception;

public class InsufficientAmountException extends Exception {
	public InsufficientAmountException() {
		super("Insufficient Amount");
	}
}
