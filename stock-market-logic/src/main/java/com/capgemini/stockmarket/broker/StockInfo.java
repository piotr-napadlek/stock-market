package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.banking.CurrencyAmount;
import com.capgemini.stockmarket.dto.CompanyTo;

public interface StockInfo {

	public Date dateBought();
	public CompanyTo company();
	public CurrencyAmount priceBought();
	public Long getStockId();
}
