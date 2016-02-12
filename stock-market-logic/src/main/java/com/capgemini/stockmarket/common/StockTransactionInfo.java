package com.capgemini.stockmarket.common;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;

public class StockTransactionInfo {
	private double unitPrice;
	private CompanyTo company;
	private int amount;
	private Currency currency;
	
	// TODO: pnapadle implement fluent builder here;
	public StockTransactionInfo(CompanyTo company, int amount, double pricePerUnit,
			Currency currency) {
		this.company = company;
		this.amount = amount;
		this.unitPrice = pricePerUnit;
		this.currency = currency;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public CompanyTo getCompany() {
		return company;
	}

	public int getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}
}
