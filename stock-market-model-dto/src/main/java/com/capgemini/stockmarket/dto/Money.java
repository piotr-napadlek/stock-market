package com.capgemini.stockmarket.dto;

public class Money {

	private Currency currency;
	private double amount;
	private String bankSignature;
	
	public Money(Currency currency, double amount, String signature) {
		this.currency = currency;
		this.amount = amount;
		this.bankSignature = signature;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	public double getAmount() {
		return amount;
	}
	public String getBankSignature() {
		return bankSignature;
	}
}
