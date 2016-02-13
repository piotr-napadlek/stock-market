package com.capgemini.stockmarket.broker;

import com.capgemini.stockmarket.dto.CompanyTo;

public interface StockPriceInformer {
	double getCurrentStockPrice(CompanyTo company);
}
