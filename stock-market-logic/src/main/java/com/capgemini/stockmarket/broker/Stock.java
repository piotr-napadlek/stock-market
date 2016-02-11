package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.banking.CurrencyAmount;
import com.capgemini.stockmarket.dto.CompanyTo;

public class Stock implements StockInfo {

	private CompanyTo company;
	private CurrencyAmount priceBought;
	private Date dateBought;
	private String token;
	private Long shareId;

	public CompanyTo getCompany() {
		return company;
		
	}

	private Stock() {
		
	}

	public static Stock createShare(BrokersOffice creator, CompanyTo company) {
		return null;
		
	}

	public void setBuyPrice(double price) {
		
	}

	public void setDateBought(Date date) {
		
	}

	@Override
	public Long getStockId() {
		return null;
	}

	@Override
	public Date dateBought() {
		return null;
	}

	@Override
	public CompanyTo company() {
		return null;
	}

	@Override
	public CurrencyAmount priceBought() {
		return null;
	}

}
