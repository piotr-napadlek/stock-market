package com.capgemini.stockmarket.common;

import com.capgemini.stockmarket.dto.CompanyTo;

public class StockTransactionInfo {
	private double unitPrice;
	private CompanyTo company;
	private int amount;
	
	public StockTransactionInfo(CompanyTo company, int amount, double pricePerUnit) {
		this.company = company;
		this.amount = amount;
		this.unitPrice = pricePerUnit;
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
}
