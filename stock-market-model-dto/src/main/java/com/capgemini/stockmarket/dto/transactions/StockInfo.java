package com.capgemini.stockmarket.dto.transactions;


import java.util.Date;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;

public interface StockInfo {

	public Date dateBought();
	public CompanyTo getCompany();
	public double priceBought();
	public Currency currencyBought();
	public String getStockId();
}
