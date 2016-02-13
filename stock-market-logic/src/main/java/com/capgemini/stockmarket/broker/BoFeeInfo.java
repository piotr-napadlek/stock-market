package com.capgemini.stockmarket.broker;

import com.capgemini.stockmarket.dto.Currency;

public class BoFeeInfo {
	private Currency currency;
	private double rate;
	private double minFee;
	
	public BoFeeInfo(Currency currency, double rate, double minFee) {
		this.currency = currency;
		this.rate = rate;
		this.minFee = minFee;
	}

	public BoFeeInfo() {
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getMinFee() {
		return minFee;
	}

	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}

}
