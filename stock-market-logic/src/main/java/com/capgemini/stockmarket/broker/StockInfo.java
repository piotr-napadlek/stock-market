package com.capgemini.stockmarket.broker;


import java.util.Date;

import com.capgemini.stockmarket.banking.Money;
import com.capgemini.stockmarket.dto.CompanyTo;

public interface StockInfo {

	public Date dateBought();
	public CompanyTo company();
	public Money priceBought();
	public Long getStockId();
}
