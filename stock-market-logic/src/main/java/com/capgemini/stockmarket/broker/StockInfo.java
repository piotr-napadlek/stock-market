package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.dto.CompanyTo;
import com.capgemini.stockmarket.dto.Money;

public interface StockInfo {

	public Date dateBought();
	public CompanyTo company();
	public Money priceBought();
	public String getStockId();
}
