package com.capgemini.stockmarket.banking;

public class CurrencyAmount {

	private Currency currency;
	private double amount;
	private String bankSignature;
	
	public CurrencyAmount(Currency currency, double amount) {
		this.currency = currency;
		this.amount = amount;
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
