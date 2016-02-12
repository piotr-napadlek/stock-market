package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Currency;
import com.capgemini.stockmarket.dto.Money;

public interface StockInfo {

	public Date dateBought();
	public CompanyTo company();
	public double priceBought();
	public Currency currencyBought();
	public String getStockId();
}
