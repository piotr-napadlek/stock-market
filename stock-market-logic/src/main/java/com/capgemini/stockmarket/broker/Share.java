package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.banking.CurrencyAmount;
import com.capgemini.stockmarket.dto.CompanyTo;

public class Share {

	private CompanyTo company;
	private CurrencyAmount priceBought;
	private Date dateBought;
	private String token;
	private Long shareId;

	public CompanyTo getCompany() {
		return company;
		
	}

	private Share() {
		
	}

	public static Share createShare(BrokersOffice creator, CompanyTo company) {
		return null;
		
	}

	public void setBuyPrice(double price) {
		
	}

	public void setDateBought(Date date) {
		
	}

	public Long getShareId() {
		return null;
		
	}

}
