package com.capgemini.stockmarket.dto;

import java.util.Date;

public class StockPriceRecordTo {
	private Date date;
	private CompanyTo company;
	private double price;
	
	public StockPriceRecordTo() {
	}

	public StockPriceRecordTo(Date date, CompanyTo company, double price) {
		this.date = date;
		this.company = company;
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CompanyTo getCompany() {
		return company;
	}

	public void setCompany(CompanyTo company) {
		this.company = company;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
