package com.capgemini.stockmarket.broker.datamanager;

import com.capgemini.stockmarket.dto.CompanyTo;

public interface StockPriceInformer {
	double getCurrentStockPrice(CompanyTo company);
}
